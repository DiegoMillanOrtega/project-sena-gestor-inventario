import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  TemplateRef,
  ViewChild,
  ViewContainerRef,
  inject,
} from '@angular/core';
import { Pedido } from '../../../../../model/pedido.model';
import { InventoryService } from '../../../../../service/inventory.service';
import { Inventory } from '../../../../../model/inventory.model';
import { AlertsService } from '../../../../../alerts/alerts.service';
import { ClientService } from '../../../../../service/client.service';
import { Client } from '../../../../../model/client.model';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { PedidoRequest } from '../../../../../model/pedido-request';
import { PedidoService } from '../../../../../service/pedido.service';
import { PedidoDetalleService } from '../../../../../service/pedido-detalle.service';
import { ToastsService } from '../../../../../service/toasts.service';
import { FormaPagoService } from '../../../../../service/forma-pago.service';
import { FormaPago } from '../../../../../model/forma-pago.model';
import { PedidoDetalle } from '../../../../../model/pedidoDetalle.model';
import Swal from 'sweetalert2';
import * as bootstrap from 'bootstrap';
import { AppComponent } from '../../../../../app.component';
import { Modal } from 'bootstrap';
import { format } from 'date-fns';
import {
  catchError,
  debounceTime,
  map,
  Observable,
  of,
  switchMap,
  timer,
} from 'rxjs';
import { PedidoDTO } from '../../../../../model/pedidoDTO.model';

@Component({
  selector: 'app-list-pedidos',
  templateUrl: './list-pedidos.component.html',
  styleUrl: './list-pedidos.component.css',
})
export class ListPedidosComponent implements OnInit, AfterViewInit {
  @ViewChild('modal') formaPagoModal!: ElementRef;

  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (
      event.target instanceof HTMLInputElement ||
      event.target instanceof HTMLTextAreaElement
    ) {
      return; // No hacer nada si el foco está en un input o textarea
    }

    if (event.key === '1') {
      this.showAndAddProducts();
    }
    if (event.key === '2') {
      this.showSelectedProducts();
    }
    if (event.key === '3') {
      this.showClients();
    }
  }

  private clientService = inject(ClientService);
  private inventoryService = inject(InventoryService);
  private pedidoService = inject(PedidoService);
  private pedidoDetalleService = inject(PedidoDetalleService);
  private alerts = inject(AlertsService);
  private cdr = inject(ChangeDetectorRef);
  private toastService = inject(ToastsService);
  private formaPagoService = inject(FormaPagoService);
  private viewContainerRef = inject(ViewContainerRef);

  selectedProducts: Inventory[] = [];
  Products: Inventory[] = [];
  productsNotSelected: Inventory[] = [];
  listPedidos: Pedido[] = [];
  clients: Client[] = [];
  productoIds: number[] = [];
  cantidades: number[] = [];
  formasDePago: FormaPago[] = [];
  selectedProductss = [
    { product: 'Producto 1', stock: 2, price: 50 },
    { product: 'Producto 2', stock: 1, price: 100 },
  ];

  valorTotalPedido: number = 0;
  stockAnterior: number = 0;
  clientSelected: string = '';
  stock: number = 0;
  //clienteABuscar: number = 0;
  labelCliente: FormGroup;
  formaPagoForm: FormGroup;
  confirmedDelivery: boolean = false;
  productoAgregadoAlaForma: boolean = false;
  clienteEncontrado: boolean = false;
  productoAgregadoToForm: boolean = false;
  stockModificado = false; // Flag para controlar si el stock ha sido modificado
  private modalInstance: any;
  pedido?: Pedido;
  clienteSeleccionado?: Client;
  fechaActual?: String;
  validFormaPago: boolean = false;
  esVisibleModalFormaPago: boolean = false;
  pedidoList: PedidoDTO[] = [];

  constructor(private form: FormBuilder) {
    this.labelCliente = form.group({
      id: ['', Validators.required],
      product: [''],
      category: ['', Validators.required],
      price: [''],
      address: ['', Validators.required],
      stock: ['', this.stockValidator.bind(this)],
      paymentType: ['', Validators.required],
      client: ['', Validators.required, this.buscarCliente.bind(this)],
    });
    this.fechaActual = format(new Date(), 'dd-MM-yyyy');

    this.formaPagoForm = form.group({
      formaPago1: [''],
      formaPago2: [''],
      numTarjeta1: [''],
      numTarjeta2: [''],
      valorPago1: ['', Validators.min(0)],
      valorPago2: ['', Validators.min(0)],
      descuento: ['', [Validators.max(100), Validators.min(0)]],
      totalValor: [''],
    });
  }

  ngAfterViewInit(): void {
    this.labelCliente.get('stock')?.valueChanges.subscribe((value) => {
      if (this.stockModificado !== true) {
        this.stockModificado = true;
        this.labelCliente.get('stock')?.updateValueAndValidity();
      }
    });
    document.body.appendChild(this.formaPagoModal.nativeElement);
    this.modalInstance = new Modal(this.formaPagoModal.nativeElement);
    
    this.formaPagoModal.nativeElement.addEventListener('hide.bs.modal', () => {
      this.formaPagoForm.reset();
    })
  }

  ngOnInit(): void {
    this.labelCliente.get('product')?.disable();
    this.labelCliente.get('price')?.disable();
    this.labelCliente.get('stock')?.disable();

    this.loadProducts();
    this.loadListPedidos();
    this.loadClients();
    this.loadFormasDePago();

    const modalElement = document.getElementById('productosModal');
    // const paymentModal = document.getElementById('paymentModal');

    if (modalElement) {
      document.body.appendChild(modalElement);
    }
    // if (paymentModal) {
    //   document.body.appendChild(paymentModal);
    // }

    // const modalElementt = this.paymentModal.nativeElement;
    // modalElementt.addEventListener('hide.bs.modal', (event: any) => {
    //   if (this.formaPagoForm.invalid) {
    //     event.preventDefault(); // Evitar el cierre del modal si el formulario es inválido
    //     alert('El formulario es inválido. Por favor, completa todos los campos.');
    //   }
    // });
  }

  loadListPedidos(): void {
    this.pedidoService.getPedidoList().subscribe(
      pedidos => {this.pedidoList = pedidos, console.log(pedidos)},
      error => console.error(error)
    )
    //this.selectedProducts = this.inventoryService.getSelectedProducts();
  }

  loadFormasDePago(): void {
    this.formaPagoService.getAllFormasDePago().subscribe(
      (response) => {
        this.formasDePago = response;
      },
      (error) => console.log('Error al obtener las formas de pago: ' + error)
    );
  }

  loadProducts(): void {
    this.inventoryService.getInventoryList().subscribe({
      next: (data) => {
        this.Products = data.filter((producto) => producto.stock > 0);
      },
      error: (error) =>
        this.alerts.mostrarMensajeError(
          'Error al obtener los productos: ' + error
        ),
    });
  }

  loadClients(): void {
    this.clientService.findAllClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (error) =>
        this.alerts.mostrarMensajeError(
          'Error al obtener los clientes: ' + error
        ),
    });
  }

  loadProductsNotSelected(): void {
    this.productsNotSelected = this.Products.filter((product) => {
      return !this.selectedProducts.some(
        (selected) => selected.id === product.id
      );
    });
  }

  // Validador personalizado para el stock
  stockValidator(control: AbstractControl): ValidationErrors | null {
    if (this.stockModificado && control.value > this.stock) {
      return { stockExceeded: true }; // Si el stock ingresado supera el disponible, devuelve un error
    }
    return null; // Si es válido, no hay errores
  }

  buscarCliente(control: AbstractControl): Observable<ValidationErrors | null> {
    const id = control.value;

    return timer(500).pipe(
      switchMap(() => {
        return this.clientService.findClientById(Number(id)).pipe(
          map((data) => {
            console.log('Encontrado: ', data);
            return data ? null : { clienteNoEncontrado: true }; // null si lo encuentra, objeto de error si no
          }),
          catchError((error) => {
            console.log('Error al buscar cliente: ' + error);
            return of({ clienteNoEncontrado: true }); // Error de búsqueda lo trata como "no encontrado"
          })
        );
      })
    );
  }

  getTotal(): number {
    return this.selectedProducts.reduce((total, producto) => {
      return total + Number(producto.price) * producto.stock;
    }, 0);
  }

  getValorFormaPago(): number {
    let totalValor =
      Number(this.formaPagoForm.get('valorPago1')?.value) +
      Number(this.formaPagoForm.get('valorPago2')?.value);
    if (this.formaPagoForm.get('descuento')?.value) {
      const valorDescuento =
        totalValor * (Number(this.formaPagoForm.get('descuento')?.value) / 100);
      totalValor -= valorDescuento;
    }
    return totalValor;
  }

  sendPedido() {
    let pedido: Pedido = {
      price: this.getTotal(),
      address: this.labelCliente.get('address')?.value,
      client: this.clienteSeleccionado!,
      paymentType:
        this.formasDePago[this.labelCliente.get('paymentType')?.value],
      pedidoDetalles: this.selectedProducts.map((product) => {
        return {
          producto: product,
        };
      }),
    };

    this.pedido = pedido;
    console.log('pedido: ', pedido);

    this.pedidoService.savePedido(pedido).subscribe(
      (response) => {
        console.log(response);
        this.labelCliente.reset();
        this.loadClients();
        this.loadProducts();
        this.loadProductsNotSelected();
        this.toastService.showToast(
          'Pedido guardado con éxito',
          'El pedido ha sido registrado correctamente.',
          'success'
        );
      },
      (error) => {
        console.error('Error al guardar el pedido', error);
      }
    );
  }

  selectedCliente(trElement: HTMLTableRowElement): void {
    const index = Array.from(trElement.parentNode?.children ?? []).indexOf(
      trElement
    );
    const cliente = this.clients[index];

    if (!this.clients.some((p) => p.id === index)) {
      this.labelCliente.get('client')?.setValue(cliente.id);
      this.clienteEncontrado = true;
      this.clienteSeleccionado = cliente;
      this.clients.splice(index, 1);
      this.alerts.cerrarAlerta();
    }
  }

  toggleConfirmation(producto: Inventory): void {
    producto.selected = !producto.selected;
    if (producto.selected) {
      this.selectedProducts.push(producto);
    } else {
      this.selectedProducts = this.selectedProducts.filter(
        (p) => p.id !== producto.id
      );
    }
  }

  removerProductos(index: number) {
    const product = this.selectedProducts[index].product;

    this.toastService.showToast(
      'Eliminado',
      `El producto "${this.selectedProducts[index].product}" fue eliminado.`,
      'danger',
      2000
    );
    this.selectedProducts.splice(index, 1);

    if (product === this.labelCliente.get('product')?.value) {
      //Limpiar inputs
      this.labelCliente.get('product')?.setValue('');
      this.labelCliente.get('price')?.setValue('');
      this.labelCliente.get('stock')?.setValue('');
      this.labelCliente.get('stock')?.disable();

      this.resetIndividualControls();
      this.showSelectedProducts();
    }
  }

  seleccionarProductos(selectedProducts: Inventory[]): void {
    if (this.selectedProducts.length > 0) {
      this.inventoryService.setSelectedProducts(selectedProducts);
      this.cdr.detectChanges();
    } else {
      this.alerts.mostrarMensajeError('No se han seleccionados productos.');
    }
  }

  removerProductosNoSeleccionados(index: number) {
    if (index >= 0 && index < this.productsNotSelected.length) {
      this.productsNotSelected.splice(index, 1); // Elimina el producto de la lista
    }
  }

  addProductToSelection(trElement: HTMLTableRowElement): void {
    const id = trElement.getAttribute('data-id'); // Obtener el identificador único

    if (id) {
      const product = this.productsNotSelected.find((p) => p.id === Number(id));

      if (product && !this.selectedProducts.some((p) => p.id === product.id)) {
        this.selectedProducts.push(product);
        this.productsNotSelected = this.productsNotSelected.filter(
          (p) => p.id !== Number(id)
        );
        const index = this.productsNotSelected.indexOf(product);
        this.productsNotSelected.splice(index, 1);
      }
    }

    if (this.productsNotSelected.length > 0) {
      this.showAndAddProducts();
      this.toastService.showToast(
        'Producto Agregado',
        `El producto se agregó correctamente`,
        'success',
        2000
      );
    } else {
      this.alerts.mostrarMensajeError('No hay productos');
    }
  }

  addProductToForm(trElement: HTMLTableRowElement): void {
    const index = Array.from(trElement.parentNode?.children ?? []).indexOf(
      trElement
    );
    // Hacemos una copia del producto antes de asignarlo al form
    const product = { ...this.selectedProducts[index] };
    this.labelCliente.patchValue(product);
    this.productoAgregadoAlaForma = true;
    this.productoAgregadoAlaForma = true;

    const id = this.labelCliente.get('id')?.value;

    // Obtener el indexProducts
    const indexProducto = this.Products.findIndex(
      (products) => products.id === id
    );

    // Actualiza el valor del stock con el stock original
    this.stock = this.Products[indexProducto].stock;

    this.labelCliente.get('stock')?.enable();
    // Actualiza el campo 'stock' en el formulario con el valor original
    this.labelCliente.get('stock')?.setValue(this.stock);

    this.alerts.cerrarAlerta();
    this.productoAgregadoToForm = true;
  }

  actualizarProducto() {
    const stockControl = this.labelCliente.get('stock');

    if (stockControl && stockControl.valid) {
      const id = this.labelCliente.get('id')?.value;

      // Actualizar el stock en el selectedProducts
      const index = this.selectedProducts.findIndex(
        (selected) => selected.id === id
      );

      if (index !== -1) {
        this.selectedProducts[index] = {
          ...this.selectedProducts[index], // Copiamos las propiedades del producto
          stock: this.labelCliente.get('stock')?.value, // Actualizamos solo el stock
        };

        this.toastService.showToast(
          'Actualizado',
          `El producto "${this.selectedProducts[index].product}" fue actualizado con éxito`,
          'success',
          5000
        );
      }
    }
  }

  calcularPrecioXStock(precio: number, stock: number) {
    const totalPrecioXStock = precio * stock;
    this.valorTotalPedido += totalPrecioXStock;
    console.log(this.valorTotalPedido);
  }

  resetIndividualControls() {
    this.labelCliente.get('product')?.reset();
    this.labelCliente.get('price')?.reset();
    this.labelCliente.get('stock')?.reset();
  }

  showModalFormasDePago() {
    this.validFormaPago = false;
  }

  showClients() {
    const columns = [
      { key: 'id', title: 'Id' },
      { key: 'name', title: 'Nombre' },
      { key: 'lastName', title: 'Apellido' },
      { key: 'address', title: 'Dirección' },
      { key: 'email', title: 'Email' },
      { key: 'phoneNumber', title: 'Teléfono' },
    ];

    this.alerts.mostrarTabla(this.clients, 'Clientes', columns, 'id', undefined, [
      {
        label: 'Añadir',
        callback: (trElement: HTMLTableRowElement) =>
          this.selectedCliente(trElement),
        class: 'btn-info',
      },
    ]);
  }

  showSelectedProducts() {
    if (this.selectedProducts.length > 0) {
      const columns = [
        { key: 'id', title: 'Id' },
        { key: 'product', title: 'Producto' },
        { key: 'category', title: 'Categoría' },
        { key: 'stock', title: 'Cantidad' },
        { key: 'price', title: 'Precio' },
      ];

      this.alerts.mostrarTabla(
        this.selectedProducts,
        'Productos Seleccionados',
        columns,
        'id',
        this.removerProductos.bind(this),
        [
          {
            label: 'Detalles',
            callback: (trElement: HTMLTableRowElement) =>
              this.addProductToForm(trElement),
            class: 'btn-info',
          },
        ]
      );
    } else {
      this.alerts.mostrarMensajeError('No hay productos para mostrar  ');
    }
  }

  showAndAddProducts() {
    this.loadProductsNotSelected();
    if (this.productsNotSelected.length > 0) {
      const columns = [
        { key: 'id', title: 'Id' },
        { key: 'product', title: 'Producto' },
        { key: 'category', title: 'Categoria' },
        { key: 'stock', title: 'Cantidad' },
        { key: 'price', title: 'Precio' },
      ];

      this.alerts.mostrarTabla(
        this.productsNotSelected,
        'Agregar Productos no Seleccionados',
        columns,
        'id',
        undefined,
        [
          {
            label: 'Añadir',
            callback: (trElement: HTMLTableRowElement) =>
              this.addProductToSelection(trElement),
            class: 'btn-info',
          },
        ],
        6,
        true
      );
    } else {
      this.alerts.mostrarMensajeError('No hay productos');
    }
  }
  submitPayment() {
    this.validFormaPago = true;
    const valorPago1 = this.formaPagoForm.get('valorPago1')?.value;
    const valorPago2 = this.formaPagoForm.get('valorPago2')?.value;
    const formaPago1: string = this.formaPagoForm.get('formaPago1')?.value;
    const formaPago2:string = this.formaPagoForm.get('formaPago2')?.value;

    if (formaPago1 || formaPago2) {
      if (valorPago1 <= 0 && valorPago2 <= 0) {
        this.validFormaPago = false;
      }
      if (formaPago1 === 'Tarjeta Debito' || formaPago1 === 'Tarjeta Credito') {
        console.log('formaPago1: ',formaPago1, ' valor: ',valorPago1, ' numTarjeta1: ',  this.formaPagoForm.get('numTarjeta1')?.value)
        if (
          valorPago1 <= 0 ||
          this.formaPagoForm.get('numTarjeta1')?.value === ''
        ) {
          this.validFormaPago = false;
        }
      }
      if (formaPago2 == 'Tarjeta Debito' || formaPago2 == 'Tarjeta Credito') {
        if (
          valorPago2 <= 0 ||
          this.formaPagoForm.get('numTarjeta2')?.value <= 0
        ) {
          this.validFormaPago = false;
        }
      }
    }

    if (this.validFormaPago) {
      this.modalInstance?.hide();
      const backdrop = document.querySelector('.modal-backdrop');
      if (backdrop) {
        backdrop.remove();
      }
      this.toastService.showToast(
        'Forma de Pago Guardada',
        'Se ha guarda las formas de pago correctamente!',
        'success',
        3300
      );
    } else {
      this.toastService.showToast(
        'El formulario hhh es inválido',
        ' Por favor, completa todos los campos.',
        'warning',
        3500
      );
    }
  }

  cancelSubmitPayment() {
    this.formaPagoForm.reset();
  }
}
