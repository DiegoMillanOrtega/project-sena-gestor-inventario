import {
  AfterViewInit,
  Component,
  ElementRef,
  HostListener,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InventoryService } from '../../../../service/inventory.service';
import Swal from 'sweetalert2';
import { CurrencyPipe } from '@angular/common';
import { PedidoService } from '../../../../service/pedido.service';
import { ClientService } from '../../../../service/client.service';
import { PrefixService } from '../../../../service/prefix.service';
import { Prefijo } from '../../../../model/prefijo.model';
import { AlertsService } from '../../../../alerts/alerts.service';
import { ConsecutiveService } from '../../../../service/consecutive.service';
import { Client } from '../../../../model/client.model';
import { Inventory } from '../../../../model/inventory.model';
import { ToastsService } from '../../../../service/toasts.service';
import { FormaPago } from '../../../../model/forma-pago.model';
import { FormaPagoService } from '../../../../service/forma-pago.service';
import { Modal } from 'bootstrap';
import { Pedido } from '../../../../model/pedido.model';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css',
})
export class PedidosComponent implements OnInit, AfterViewInit {
  @ViewChild('formaPago') formaPagoModal!: ElementRef;
  @ViewChild('editarProductoModal') editProductoModal!: ElementRef;
  @ViewChild('confirmModal') modalDeConfirmacion!: ElementRef;

  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (
      event.target instanceof HTMLInputElement ||
      event.target instanceof HTMLTextAreaElement
    ) {
      return; // No hacer nada si el foco está en un input o textarea
    }

    if (event.key === '1') {
      this.showProductsNotSelected();
    }
    if (event.key === '2') {
      this.showSelectedProducts();
    }
    if (event.key === '3') {
      this.showModalFormasDePago();
    }
  }

  private routed = inject(ActivatedRoute);
  private _inventoryService = inject(InventoryService);
  private pedidoService = inject(PedidoService);
  private route = inject(Router);
  private serviceClient = inject(ClientService);
  private prefixService = inject(PrefixService);
  private consecutivoService = inject(ConsecutiveService);
  private alerts = inject(AlertsService);
  private inventoryService = inject(InventoryService);
  private toastService = inject(ToastsService);
  private formaPagoService = inject(FormaPagoService);

  formaPagoForm: FormGroup;
  productoForm: FormGroup;
  productos: Inventory[] = [];
  productsNotSelected: Inventory[] = [];
  selectedProducts: Inventory[] = [];
  indexProductoEliminar: number = -1;
  productId: number = 0;
  productName: string = '';
  clientSelected: string = '';
  clients: Client[] = [];
  stockOriginal: number = 0;
  stockProductoNoSeleccionado: number = 0;
  productoNuevo: boolean = false;
  loading: boolean = true;
  prefixList: Prefijo[] = [];
  formFV: FormGroup;
  nameClients: string[] = [];
  formasDePago: FormaPago[] = [];
  validFormaPago: boolean = false;
  mostrar: boolean = false;
  total: number = 0;
  modalDetalleProducto!: Modal;
  modalConfirmacionInstancia!: Modal;
  productoSeleccionado!: Inventory;
  indexProductoSeleccionado!: number;
  vcantVenderAnterior: number = 0;
  controlCantidadVender = new FormControl(0);

  [key: string]: any

  formStructure = [
    { label: 'Producto', name: 'product', type: 'text' },
    { label: 'Precio', name: 'price', type: 'text' },
    { label: 'Stock Disponible', name: 'stock', type: 'number' },
    { label: 'Cantidad a Vender', name: 'stock', type: 'number' },
  ];

  columnsProducts = [
    { key: 'id', title: 'Id' },
    { key: 'product', title: 'Producto' },
    { key: 'category', title: 'Categoría' },
    { key: 'stock', title: 'Existencias' },
    { key: 'price', title: 'Precio' },
  ];

  columnsProductosDataTable = [
    { key: 'id', title: 'Id' },
    { key: 'product', title: 'Producto' },
    { key: 'category', title: 'Categoría' },
    { key: 'cantVender', title: 'Cantidad' },
    { key: 'price', title: 'Precio' },
  ];

  columnsCliente = [
    { key: 'id', title: 'Id' },
    { key: 'name', title: 'Nombre' },
    { key: 'lastName', title: 'Apellido' },
    { key: 'phoneNumber', title: 'Celular' },
    { key: 'email', title: 'Email' },
  ];

  buttons = [
    {
      icon: '<i class="bi bi-gear"></i>',
      id: 'editar',
      class: 'btn-secondary',
    },
    { icon: '<i class="bi bi-trash3">', id: 'eliminar', class: 'btn-danger' },
  ];
  modalInstance: any;
  editProductoModalBootstrap?: any;

  constructor(private form: FormBuilder) {
    this.formFV = this.form.group({
      basicInfoFV: this.form.group({
        prefijo: [''],
        numero: [''],
        fecha: [''],
      }),
      clienteForm: this.form.group({
        id: [''],
        name: [''],
        lastName: [''],
        address: [''],
        phoneNumber: [''],
        email: [''],
        gender: [''],
      }),
      vendedorForm: this.form.group({
        id: [''],
        name: [''],
        lastName: [''],
        address: [''],
        phoneNumber: [''],
        email: [''],
        gender: [''],
      }),
    });
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

    this.productoForm = form.group({
      id: [null, Validators.required],  
      product: [{ value: '', disabled: false }, Validators.required],
      category: [null, Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      stock: ['', Validators.required],
      cantVender: [{ value: '', disabled: false }, [Validators.min(1), this.stockValidator.bind(this)]],
    });

    this.controlCantidadVender = this.productoForm.get(
      'cantVender'
    ) as FormControl;
  }
  ngAfterViewInit(): void {
    // Modal de forma de pago
    document.body.appendChild(this.formaPagoModal.nativeElement);
    this.modalInstance = new Modal(this.formaPagoModal.nativeElement);
    document.body.appendChild(this.editProductoModal.nativeElement);
    this.modalDetalleProducto = new Modal(this.editProductoModal.nativeElement);

     // Suscribirte a los cambios de valor una vez
     this.controlCantidadVender.valueChanges.subscribe((valorActual) => {
      const cantVender: number = Number(valorActual);
      this.procesarCambioStock(cantVender);
    });

    this.formaPagoModal.nativeElement.addEventListener('hide.bs.modal', () => {
      this.formaPagoForm.reset();
    });
  }

  ngOnInit(): void {
    this.loadProducts();
    this.loadClients();
    this.loadPrefix();
    this.loadFormasDePago();

    const modalElement = document.getElementById('productosModal');
    // const paymentModal = document.getElementById('paymentModal');

    if (modalElement) {
      document.body.appendChild(modalElement);
    }

   

    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }

  loadClients() {
    this.serviceClient.findAllClients().subscribe(
      (clients) => {
        this.clients = clients;
        console.log(clients);
      },
      (error) =>
        console.error('Error al cargar lista de nombres de cliente ', error)
    );
  }

  loadFormasDePago(): void {
    this.formaPagoService.getAllFormasDePago().subscribe(
      (response) => {
        this.formasDePago = response;
      },
      (error) => console.log('Error al obtener las formas de pago: ' + error)
    );
  }

  loadPrefix(): void {
    this.prefixService.getAllPrefix().subscribe(
      (data) => {
        this.prefixList = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  loadProducts(): void {
    this.inventoryService.getInventoryList().subscribe({
      next: (data) => {
        this.productos = data.filter((producto) => producto.stock > 0);
        // Realizar una copia profunda de los productos
      this.productsNotSelected = this.productos.map(producto => ({ ...producto }));
      },
      error: (error) =>
        this.alerts.mostrarMensajeError(
          'Error al obtener los productos: ' + error
        ),
    });
  }

  //Validators - Form

  stockValidator(control: AbstractControl): ValidationErrors | null {
    if (this.stockOriginal == null && this.stockProductoNoSeleccionado == null) {
      return null;  // Evita validar si los valores de stock no están definidos
    }
    
    if (this.productoNuevo) {
      if (control.value > this.stockOriginal) { return { stockExceeded: true} }
    } else {
      if (control.value > this.stockOriginal) { return { stockExceeded: true} }
    }
    
    // if (control.value > this.stockProductoNoSeleccionado && !this.productoNuevo && ) {
    //   return { stockExceeded: true} 
    // }

    // if (control.value === this.stockOriginal && this.stockProductoNoSeleccionado) {
    //   return null
    // }
    
    if (control.value > this.stockOriginal) {
      return { stockExceeded: true} 
    }
    

    return null
  }

  setPrefixToInput(trElement: HTMLTableRowElement): void {
    const basicInfoFV = this.formFV.get('basicInfoFV') as FormGroup;
    const id = trElement.getAttribute('data-id'); // Aquí obtienes el ID como string

    if (id) {
      const prefijo = this.prefixList.find(
        (prefix) => prefix.prefixId === Number(id)
      );

      if (prefijo) {
        basicInfoFV.get('prefijo')?.setValue(prefijo.prefix);
        this.consecutivoService.getConsecutive(prefijo.prefix).subscribe(
          (consecutivo) => basicInfoFV.get('numero')?.setValue(consecutivo),
          (error) => console.error(error)
        );
      }
      this.alerts.cerrarAlerta();
    }
  }

  onInputExitPrefix(prefijo: string) {
    const basicInfoFV = this.formFV.get('basicInfoFV') as FormGroup;

    basicInfoFV.get('numero')?.enable();
    const prefijoExistencia = this.prefixList.find(
      (p) => p.prefix === prefijo.trim()
    );

    if (prefijo.trim() === '') {
      basicInfoFV.get('prefijo')?.setErrors(null);
      return;
    }

    if (prefijoExistencia) {
      this.consecutivoService
        .getConsecutive(prefijoExistencia.prefix)
        .subscribe(
          (consecutivo) => {
            basicInfoFV.get('numero')?.setValue(consecutivo),
              basicInfoFV.get('prefijo')?.setErrors(null);
          },
          (error) => console.error(error)
        );
    } else {
      basicInfoFV.get('prefijo')?.setErrors({ invalidPrefix: true });
      basicInfoFV.get('numero')?.disable();
    }
  }

  onInputChange(event: Event) {
    const basicInfoFV = this.formFV.get('basicInfoFV') as FormGroup;

    const inputElement = event.target as HTMLInputElement;
    inputElement.value = inputElement.value.toUpperCase();
    basicInfoFV
      .get('prefijo')
      ?.setValue(inputElement.value.toUpperCase(), { emitEvent: false });
  }

  onButtonClicked(event: {btn: string, data?:any}) {
    if (event.btn === 'editar') {
      document.body.appendChild(this.editProductoModal.nativeElement);
      if (event.data) {
        this.productoSeleccionado = event.data
        this.showModalDetallePedido(event.data);
      }
    }
    if (event.btn === 'eliminar') {
      if (event.data !== -1) {
        this.mostrarModalDeConfirmacion(event.data);
      }
    }
  }

  selectedCliente(trElement: HTMLTableRowElement, tipoCliente: string): void {
    const clienteForm = this.formFV.get('clienteForm') as FormGroup;
    const vendedorForm = this.formFV.get('vendedorForm') as FormGroup;

    const index = Array.from(trElement.parentNode?.children ?? []).indexOf(
      trElement
    );
    const cliente = this.clients[index];

    if (!this.clients.some((p) => p.id === index)) {
      tipoCliente === 'cliente'
        ? clienteForm.setValue(cliente)
        : vendedorForm.setValue(cliente);

      console.log(clienteForm.value);
      this.clients.splice(index, 1);
      this.alerts.cerrarAlerta();
    }
  }

  showModalFormasDePago() {
    this.modalInstance.show();
    this.validFormaPago = false;
  }

  submitPayment() {
    this.validFormaPago = true;
    const valorPago1 = this.formaPagoForm.get('valorPago1')?.value;
    const valorPago2 = this.formaPagoForm.get('valorPago2')?.value;
    const formaPago1: string = this.formaPagoForm.get('formaPago1')?.value;
    const formaPago2: string = this.formaPagoForm.get('formaPago2')?.value;

    if (formaPago1 || formaPago2) {
      if (valorPago1 <= 0 && valorPago2 <= 0) {
        this.validFormaPago = false;
      }
      if (formaPago1 === 'Tarjeta Debito' || formaPago1 === 'Tarjeta Credito') {
        console.log(
          'formaPago1: ',
          formaPago1,
          ' valor: ',
          valorPago1,
          ' numTarjeta1: ',
          this.formaPagoForm.get('numTarjeta1')?.value
        );
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

  setTotal(total: number) {
    this.total = total;
  }

  getTotal(): number {
    return this.selectedProducts.reduce((total, producto) => {
      return total + Number(producto.price) * producto.stock;
    }, 0);
  }

  sendPedido() {
    const clienteForm = this.formFV.get('clienteForm') as FormGroup;

    let pedido: Pedido = {
      price: this.getTotal(),
      address: '',
      client: clienteForm.get('id')?.value!,
      paymentType:
        this.formasDePago[this.formaPagoForm.get('paymentType')?.value],
      pedidoDetalles: this.selectedProducts.map((product) => {
        return {
          producto: product,
        };
      }),
    };

    // this.pedido = pedido;
    console.log('pedido: ', pedido);

    this.pedidoService.savePedido(pedido).subscribe(
      (response) => {
        console.log(response);
        this.formFV.reset();
        this.loadClients();
        this.loadProducts();
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

  removerProductos(index: number) {
    this.toastService.showToast(
      'Eliminado',
      `El producto "${this.selectedProducts[index].product}" fue eliminado.`,
      'success',
      2000
    );
    this.selectedProducts.splice(index, 1);

    this.showSelectedProducts();
  }

  private procesarCambioStock(cantVender: number) {
    // Solo calcular la diferencia si cantVender es menor o igual al stock disponible
    let diferencia: number = 0;

    if (cantVender === null) {
      cantVender = 0;
    }

    if (cantVender <= this.stockOriginal) {
      diferencia = Math.abs(cantVender - this.vcantVenderAnterior);
    }

    // Actualizar el stock en función del cambio
    if (cantVender > this.vcantVenderAnterior) {
      // Si está intentando vender más, reducimos el stock
      this.actualizarStock(-diferencia);
    } else if (cantVender === 0 ) {
      this.productoForm.get('stock')?.setValue(this.stockOriginal)
    } else
    if (cantVender < this.vcantVenderAnterior) {
      // Si está intentando vender menos, incrementamos el stock
      this.actualizarStock(diferencia);
    }
  }

  private actualizarStock(cambio: number) {
    this.productoForm.get('stock')?.enable();

    // Verificar si el nuevo stock sería válido
    if (this.stockOriginal + cambio >= 0 && this.stockOriginal + cambio <= this.stockOriginal) {
      this.productoForm.get('stock')?.setValue(this.stockOriginal + cambio);
    } else {
      // Si excede, restaurar el stock original
      this.productoForm.get('stock')?.setValue(this.stockOriginal);
    }

    this.productoForm.get('stock')?.disable();
  }

  

  confirmarDetalleProductoNuevo() {
    this.productoForm.get('stock')?.enable();
    this.productoForm.get('product')?.enable();
    const productoForm : Inventory = this.productoForm.value;
    const producto = this.productsNotSelected.find( p => p.id === productoForm.id)
    const productoExiste = this.selectedProducts.find( p => p.id === productoForm.id);

    if (productoExiste != undefined && producto != undefined) {
      this.confirmarDetalleProductoExistente(productoExiste, producto);
      return
    }

    if (productoForm != undefined && producto != undefined) {
      if (!productoForm.cantVender || productoForm.cantVender <= 0) {return} 
      else {
        if (productoForm.cantVender > this.stockOriginal) {
          this.toastService.showToast('Stock Insuficiente' , 'La cantidad solicitada supera el stock disponible. Por favor, ajusta la cantidad o selecciona otro producto.', 'warning', 3500)
          return
        }
        producto.stock -= productoForm.cantVender;
        this.selectedProducts.push(productoForm);
        this.modalDetalleProducto.hide();
      }
    }

    // this.productoForm.get('stock')?.enable();
    // this.productoForm.get('product')?.enable();
    // const producto: any = this.productoForm.value;
    // producto.stock = this.productoForm.get('cantVender')?.value;
    // const indexProductoSeleccionado = this.selectedProducts.findIndex(
    //   (p) => p.id === producto.id
    // );
    
    // const indexProductoNoSeleccionado = this.productsNotSelected.findIndex(
    //   (p) => p.id === producto.id
    // );

    // if (indexProductoSeleccionado !== -1) { //Si ya es un producto agregado sumar cant vender
    //   if (producto.stock > this.productsNotSelected[indexProductoNoSeleccionado].stock) {
    //     if ((this.productsNotSelected[indexProductoNoSeleccionado].stock - this.productoForm.get('cantVender')?.value) <= -1) {
    //       this.toastService.showToast('Stock insuficiente', 'La cantidad seleccionada a vender supera la disponible','info',4000)
    //       return 
    //     }
    //     const diferencia = producto.stock - this.productsNotSelected[indexProductoNoSeleccionado].stock;
    //     this.productsNotSelected[indexProductoNoSeleccionado].stock += diferencia;
    //     return
    //   }
    //   this.selectedProducts[indexProductoSeleccionado].cantVender += this.productoForm.get('cantVender')?.value;
    // } else {
    //   this.selectedProducts.push(producto);
    // }

    
    // this.productsNotSelected[indexProductoNoSeleccionado].stock -= this.productoForm.get('cantVender')?.value;

    // this.stockProductoNoSeleccionado = this.productsNotSelected[indexProductoNoSeleccionado].stock
    // this.modalDetalleProducto.hide();
  }

  confirmarDetalleProductoExistente(productoExistente: Inventory, producto: Inventory) {
    const productoForm: Inventory = this.productoForm.value;
    
    let dif = 0;
    if (productoForm != undefined && productoExistente != undefined) {
      if (!productoForm.cantVender) {
        this.toastService.showToast('Cantidad Requerida', 'Debes ingresar una cantidad para proceder con la venta. Por favor, introduce un valor válido antes de guardar.', 'warning', 3500)
      } else {
        if (productoForm.cantVender > this.stockOriginal) {
          this.toastService.showToast('Stock Insuficiente' , 'La cantidad solicitada supera el stock disponible. Por favor, ajusta la cantidad.', 'warning', 5000)
          productoForm.stock = productoExistente.stock;
        } else {
          if (productoForm.stock > productoExistente.stock) { //Se vende menos, aumentar el stock en el productNoSelected
            dif = productoForm.stock - productoExistente.stock;
            //Actualizamos el productoExistente en el array selectedProducts
            productoExistente.cantVender = productoForm.cantVender;
            productoExistente.stock += dif;
            //Actualizamos el producto en el array productsNotSelected
            producto.stock += dif;
            this.modalDetalleProducto.hide()
          } else { //se vende mas, disminuir el stock en el productNoSelected
            dif = productoExistente.stock - productoForm.stock;
            if (dif >= 0) {
              if (dif !== 0) {
                //Actualizamos el productoExistente en el array selectedProducts
                productoExistente.cantVender = productoForm.cantVender;
                productoExistente.stock -= dif;
                //Actualizamos el producto en el array productsNotSelected
                producto.stock -= dif;
              }
              this.modalDetalleProducto.hide()
            } else {
              this.toastService.showToast('Stock Insuficiente' , 'La cantidad solicitada supera el stock disponible. Por favor, ajusta la cantidad.', 'warning', 5000)
            }
          }
        }
      }
    } else {
      this.toastService.showToast('Producto No Encontrado', 'El producto seleccionado no se pudo encontrar. Puede que haya sido eliminado o modificado. Por favor, verifica e intenta nuevamente.', 'danger')
    }

  }

  eliminarProducto() {
    try {
        if (!this.indexProductoEliminar) {
            this.toastService.showToast('Error al Eliminar Producto', 'No se puede eliminar un producto vacío. Por favor, selecciona un producto válido antes de intentar eliminarlo.', 'danger', 5000);
            return;
        }
        const productoOriginal = this.productos.find(p => p.id === this.selectedProducts[this.indexProductoEliminar].id)
        const productoNoSeleccionado = this.productsNotSelected.find(p => p.id === this.selectedProducts[this.indexProductoEliminar].id)
        if (productoOriginal !== undefined && productoNoSeleccionado !== undefined) {
          //Restablecer el producto en ProductsNotSelected:
          productoNoSeleccionado.stock = productoNoSeleccionado.stock
          productoNoSeleccionado.cantVender = 0;
          this.selectedProducts.splice(this.indexProductoEliminar, 1);
          this.toastService.showToast('Producto Eliminado', 'El producto se ha eliminado correctamente de la lista. ¡La operación se realizó con éxito!', 'success', 5000); 
        } else {
          this.toastService.showToast('Error al Eliminar Producto', 'No se pudo encontrar el producto seleccionado. Asegúrate de que el producto existe en la lista antes de intentar eliminarlo.')
        }
    } catch (error) {
        console.error('Error al eliminar el producto:', error);
        this.toastService.showToast('Error al Eliminar Producto', 'Ocurrió un error inesperado. Por favor, intenta nuevamente más tarde.', 'danger', 5000);
    } finally {
        document.body.removeChild(this.modalDeConfirmacion.nativeElement);
        this.modalConfirmacionInstancia.hide();
    }
}


  mostrarModalDeConfirmacion(indexProducto: number) {
    document.body.appendChild(this.modalDeConfirmacion.nativeElement);
    this.modalConfirmacionInstancia = new Modal(this.modalDeConfirmacion.nativeElement);
    this.indexProductoEliminar = indexProducto;
    this.modalConfirmacionInstancia.show();
  }

  addProductToSelection(trElement: HTMLTableRowElement): void {
    const id = trElement.getAttribute('data-id'); // Obtener el identificador único
    console.log(id);

    if (id) {
      const product = this.productsNotSelected.find((p) => p.id === Number(id));

      if (product && product.stock > 0) {
        this.showModalDetallePedido(product);
        this.alerts.cerrarAlerta()
      } else {
        this.toastService.showToast('Error', '¡¡Producto no Encontrado ó sin Stock!!', 'danger', 3500)
      }
    } else {
      this.toastService.showToast('Error', '¡¡No se pudo obtener el id del producto!!', 'danger', 3500);
    }
  }

  showModalDetallePedido(producto: Inventory) {
    const index = this.productsNotSelected.findIndex(p => p.id === producto.id)
    const productoExiste = this.selectedProducts.findIndex(p => p.id === producto.id)
    
    this.stockOriginal = this.productos[index].stock;
    this.stockProductoNoSeleccionado = this.productsNotSelected[index].stock || 0;
    this.productoNuevo = false;
    if (productoExiste !== -1) {this.productoNuevo = true}
    
    this.productoForm.setValue({
      id: producto.id,
      product: producto.product,
      category: producto.category,
      price: producto.price,
      stock: productoExiste !== -1 ? this.stockProductoNoSeleccionado: this.stockOriginal,
      cantVender: producto.cantVender || 0,
    });
    
    this.productoForm.get('product')?.disable()
    this.productoForm.get('stock')?.disable()

    this.modalDetalleProducto.show()  
  }

  showProductsNotSelected() {
    this.alerts.mostrarTabla(
      this.productsNotSelected,
      'Productos a Seleccionar',
      this.columnsProducts,
      'id',
      undefined,
      [
        {
          label: 'Añadir',
          callback: (trElement: HTMLTableRowElement) =>
            this.addProductToSelection(trElement),
          class: 'btn btn-primary',
        },
      ],
      6,
      true
    );
  }

  showTerceros(tipoCliente: string) {
    this.alerts.mostrarTabla(
      this.clients,
      'Clientes',
      this.columnsCliente,
      'id',
      undefined,
      [
        {
          label: 'Añadir',
          callback: (trElement: HTMLTableRowElement) =>
            this.selectedCliente(trElement, tipoCliente),
          class: 'btn-info',
        },
      ]
    );
  }
  showModalEditProduct(trElement: HTMLTableRowElement) {
    if (this.editProductoModalBootstrap) {
      this.editProductoModalBootstrap.show();
    }
  }

  showSelectedProducts() {
    if (this.selectedProducts.length > 0) {
      this.alerts.mostrarTabla(
        this.selectedProducts,
        'Productos Seleccionados',
        this.columnsProducts,
        'id',
        this.removerProductos.bind(this),
        [
          {
            label: 'Editar',
            callback: (trElement: HTMLTableRowElement) =>
              this.showModalEditProduct(trElement),
          },
        ]
      );
    } else {
      this.alerts.mostrarMensajeError('No hay productos para mostrar  ');
    }
  }

  showPrefix() {
    this.alerts.mostrarTabla(
      this.prefixList,
      'Prefijos',
      [
        { key: 'prefixId', title: 'Codigo' },
        { key: 'prefix', title: 'Prefijo' },
      ],
      'prefixId',
      undefined,
      [
        {
          label: 'Añadir',
          callback: (trElement: HTMLTableRowElement) =>
            this.setPrefixToInput(trElement),
          class: 'btn btn-primary',
        },
      ],
      6
    );
  }

  mostrarInventory() {
    // if (this.selectedProducts.length < 1) {this.toastService.showToast('No hay productos seleccionados', 'Por favor, selecciona al menos un producto para continuar y poder ver los detalles.','warning',4000)}

    this.mostrar = !this.mostrar;
    if (this.selectedProducts.length > 0) {
    }
  }
}