import {
  ChangeDetectorRef,
  Component,
  HostListener,
  OnInit,
  ViewChild,
  inject,
} from '@angular/core';
import { Pedido } from '../../../../../model/pedido.model';
import { InventoryService } from '../../../../../service/inventory.service';
import { Inventory } from '../../../../../model/inventory.model';
import { AlertsService } from '../../../../../alerts/alerts.service';
import { ClientService } from '../../../../../service/client.service';
import { Client } from '../../../../../model/client.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PedidoRequest } from '../../../../../model/pedido-request';
import { PedidoService } from '../../../../../service/pedido.service';
import { PedidoDetalleService } from '../../../../../service/pedido-detalle.service';
import { ToastsService } from '../../../../../service/toasts.service';

@Component({
  selector: 'app-list-pedidos',
  templateUrl: './list-pedidos.component.html',
  styleUrl: './list-pedidos.component.css',
})
export class ListPedidosComponent implements OnInit {
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

  selectedProducts: Inventory[] = [];
  Products: Inventory[] = [];
  productsNotSelected: Inventory[] = [];
  listPedidos: Pedido[] = [];
  clients: Client[] = [];
  productoIds: number[] = [];
  cantidades: number[] = [];

  clientSelected: string = '';
  stock: number = 0;
  //clienteABuscar: number = 0;
  labelCliente: FormGroup;
  confirmedDelivery: boolean = false;
  clienteEncontrado: boolean = false;
  productoAgregadoToForm: boolean = false;

  constructor(private form: FormBuilder) {
    this.labelCliente = form.group({
      id: ['', Validators.required],
      product: ['', Validators.required],
      category: ['', Validators.required],
      price: ['', Validators.required],
      address: ['', Validators.required],
      stock: ['', Validators.required],
      paymentType: ['', Validators.required],
      client: ['']
    });
  }

  ngOnInit(): void {
    this.loadProducts();
    this.loadListPedidos();
    this.loadClients();
  }

  loadListPedidos(): void {
    this.selectedProducts = this.inventoryService.getSelectedProducts();
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

  sendPedido() {
    let pedido: Pedido = {
      price: this.labelCliente.get('price')?.value,
      address: this.labelCliente.get('address')?.value,
      client: this.clients[0],
      paymentType: this.labelCliente.get('paymentType')?.value,
    };

    for (let index = 0; index < this.selectedProducts.length; index++) {
      this.productoIds.push(this.selectedProducts[index].id);
      this.cantidades.push(this.selectedProducts[index].stock);
    }

    const pedidoRequest: PedidoRequest = {
      pedido: pedido,
      productos: this.selectedProducts,
      cantidades: this.cantidades,
    };
    console.log(pedidoRequest);
    this.pedidoService.savePedido(pedidoRequest).subscribe(
      (response) => {
        console.log('Pedido guardado con exito ', response);
      },
      (error) => {
        console.error('Error al guardar el pedido', error);
      }
    );
    // Crear el objeto pedido con la información básica
  }

  selectedCliente(trElement: HTMLTableRowElement): void {
    const index = Array.from(trElement.parentNode?.children ?? []).indexOf(
      trElement
    );
    const cliente = this.clients[index];
    const clientDetails = {
      client: cliente.name + ' ' + cliente.lastName,
    };

    if (!this.clients.some((p) => p.id === index)) {
      this.labelCliente.patchValue(clientDetails);
      this.labelCliente.get('client')?.disable();
      this.clients.splice(index, 1);
      this.alerts.cerrarAlerta();
    }
  }

  buscarCliente() {
    const id = this.labelCliente.get('client')?.value;

    this.clientService.findClientById(Number(id)).subscribe({
      next: (data) => {
        if (data) {
          this.clienteEncontrado = true;
        } else {
          this.clienteEncontrado = false;
        }
      },
      error: (error) => {
        console.log('error no se consiguio cliente' + error),
          (this.clienteEncontrado = false);
      },
    });
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
    this.toastService.showToast(
      'Eliminado',
      `El producto "${this.selectedProducts[index].product}" fue eliminado.`,
      'danger',
      2000
    );

    this.selectedProducts.splice(index, 1);
    this.showSelectedProducts();
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
    const index = Array.from(trElement.parentNode?.children ?? []).indexOf(
      trElement
    );

    if (index >= 0 && index < this.productsNotSelected.length) {
      const product = this.productsNotSelected[index];

      if (!this.selectedProducts.some((p) => p.id === product.id)) {
        this.selectedProducts.push(product);
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

    const id = this.labelCliente.get('id')?.value;

    // Obtener el indexProducts
    const indexProducto = this.Products.findIndex(
      (products) => products.id === id
    );

    // Asignar el stock max
    this.stock = this.Products[indexProducto].stock;

    // Deshabilitar los inputs
    this.labelCliente.get('id')?.disable();
    this.labelCliente.get('product')?.disable();
    this.labelCliente.get('price')?.disable();

    this.alerts.cerrarAlerta();
    this.productoAgregadoToForm = true;
  }

  actualizarProducto() {
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
        2000
      );
    }
  }

  showClients() {
    const columns = [
      { key: 'id', title: 'Id' },
      { key: 'name', title: 'Nombre' },
      { key: 'lastName', title: 'Apellido' },
      { key: 'gender', title: 'Genero' },
      { key: 'address', title: 'Dirección' },
      { key: 'email', title: 'Email' },
      { key: 'phoneNumber', title: 'Teléfono' },
    ];

    this.alerts.mostrarTabla(this.clients, 'Clientes', columns, undefined, [
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
}
