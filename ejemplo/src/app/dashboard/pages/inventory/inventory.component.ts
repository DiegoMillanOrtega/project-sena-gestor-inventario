import {
  AfterViewInit,
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
  inject,
} from '@angular/core';
import { InventoryService } from '../../../service/inventory.service';
import { Inventory } from '../../../model/inventory.model';
import Swal from 'sweetalert2';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../model/category.model';
import { AlertsService } from '../../../alerts/alerts.service';
import { Pedido } from '../../../model/pedido.model';
import { ActivatedRoute } from '@angular/router';
import { timeout } from 'rxjs';
import { ToastsService } from '../../../service/toasts.service';
import { IconsSvgComponent } from '../../../icons-svg/icons-svg.component';
import { Modal } from 'bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.css',
  providers: [CurrencyPipe]
})
export class InventoryComponent implements OnInit, AfterViewInit {
  @ViewChild('editarProductoModal') editProductoModal!: ElementRef;

  private _inventoryService = inject(InventoryService);
  private categoryService = inject(CategoryService);
  private alerts = inject(AlertsService);
  private route = inject(ActivatedRoute);
  private toast = inject(ToastsService);
  private currencyPipe = inject(CurrencyPipe);

  @Input() selectedProductsInput: Inventory[] = [];
  @Input() headerVisible: boolean = true;
  viewPedido: boolean = false;
  inventoryList: Inventory[] = [];
  originalInventoryList: Inventory[] = [];
  categoryList: Category[] = [];
  selectedProducts: Inventory[] = [];
  productName: string = '';
  productCategory: string = '';
  loading: boolean = true;
  confirmedProductDelivery: boolean = false;
  mostrarProductosSinStock: boolean = false;
  sortDirection: string = 'asc';
  editProductoModalBootstrap!: Modal;
  productoForm: FormGroup;
  dropdownOpen: boolean = false;

  constructor(private form: FormBuilder) {
    this.productoForm = form.group({
      id: [null, Validators.required],
      product: ['', Validators.required],
      category: [null, Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      stock: [0, [Validators.required, Validators.min(0)]],
    });

    this.productoForm.controls['price'].valueChanges.subscribe(value => {
      const formattedValue = this.currencyPipe.transform(value, 'USD', 'symbol', '1.2-2');
      this.productoForm.controls['price'].setValue(formattedValue, { emitEvent: false });
    });
  }

  ngAfterViewInit(): void {
    // Modal de editar productos
    // document.body.appendChild(this.editProductoModal.nativeElement);
    // this.editProductoModalBootstrap = new Modal(
    //   this.editProductoModal.nativeElement
    // );
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.mostrarProductosSinStock = params['sinStock'] === 'true';
    });

    if (this.selectedProductsInput.length > 0) {
      this.inventoryList = this.selectedProductsInput;
      this.viewPedido = true;
      this.loading = false;
    } else {
      if (this.mostrarProductosSinStock != true) {
        this.getAllProductos();
      } else {
        this.getOutOfStockProducts();
      }
    }

    this.originalInventoryList = [...this.inventoryList];
    this.getAllCategory();
  }

  async deleteProduct(id: number) {
    const resultado = await this.alerts.mostrarConfirmacion();

    if (resultado) {
      this._inventoryService.deleteProductId(id).subscribe(
        (response) => {
          this._inventoryService.getInventoryList().subscribe(
            (data) => (this.inventoryList = data),
            (error) =>
              console.error('Error al obtener la lista de inventario', error)
          );
          this.alerts.mostrarMensajeExito(
            'Eliminado',
            'El producto ha sido eliminado'
          );
        },
        (error) =>
          this.alerts.mostrarMensajeError('Error al eliminar el producto')
      );
    }
  }

  getAllProductos() {
    this._inventoryService.getInventoryList().subscribe({
      next: (data) => {
        this.inventoryList = data;
      },
      error: (error) =>
        console.error('Error al obtener la lista de inventario', error),
      complete: () => (this.loading = false),
    });
  }

  getOutOfStockProducts() {
    this._inventoryService.getOutOfStockProducts().subscribe({
      next: (data) => {
        this.inventoryList = data;
      },
      error: (error) =>
        console.error(
          'Error al obtener la lista de productos sin stock',
          error
        ),
      complete: () => (this.loading = false),
    });
  }

  getAllCategory() {
    //this.getAllProductos();
    this.categoryService.getCategoryList().subscribe(
      (data) => {
        this.categoryList = data;
      },
      (error) => console.error('Error al obtener la lista de categorias', error)
    );
  }

  buscarProductos() {
    this.loading = true;
    if (this.mostrarProductosSinStock != true) {
      this._inventoryService.searchProductByName(this.productName).subscribe({
        next: (data) => {
          this.inventoryList = data;
          if (this.mostrarProductosSinStock) {
            this.inventoryList = this.inventoryList.filter(
              (product) => product.stock < 5
            );
          }
          this.productName = '';
        },
        complete: () => {
          this.loading = false;
        },
      });
    } else {
      this.buscarProductosSinStock();
    }
  }

  buscarProductosSinStock() {
    this.loading = true;
    console.log('Producto que se va a buscar:', this.productName); // Verifica el valor enviado

    this._inventoryService
      .searchProductByNameOutOfStock(this.productName)
      .subscribe(
        (response) => {
          this.inventoryList = response;
          this.productName = '';
        },
        (error) => {
          console.error('Error al buscar productos:', error);
        }
      );
    this.loading = false;
  }

  buscarProductosByCategory(category: Category) {
    console.log('holaa');
    
    this.loading = true;
    if (this.mostrarProductosSinStock != true) {
      this._inventoryService.searchByCategory(category.category).subscribe({
        next: (data) => {
          this.inventoryList = data;
        },
        error: (error) => {
          console.log('Error al buscar el producto por Categoria: ' + error);
        },
        complete: () => {
          this.loading = false;
        },
      });
    } else {
      this.buscarProductosByCategorySinStock(category);
    }
  }

  buscarProductosByCategorySinStock(category: Category) {
    this.loading = true;

    this._inventoryService
      .searchByCategoryOutOfStock(category.category)
      .subscribe({
        next: (data) => {
          this.inventoryList = data;
        },
        error: (error) => {
          console.log(
            'Error al buscar el producto por Categoria Sin stock: ' + error
          );
        },
        complete: () => {
          this.loading = false;
        },
      });
  }

  toggleConfirmation(producto: Inventory) {
    if (producto.stock > 0) {
      producto.selected = !producto.selected;
      if (producto.selected) {
        this.selectedProducts.push(producto);
      } else {
        this.selectedProducts = this.selectedProducts.filter(
          (p) => p.id !== producto.id
        );
      }
    } else {
      this.alerts.mostrarMensajeError(
        'No hay suficiente stock para este producto.'
      );
    }
  }

  sortTable(column: keyof Inventory) {
    const direction = this.sortDirection === 'asc' ? 1 : -1;

    this.inventoryList.sort((a, b) => {
      let valueA: any = a[column];
      let valueB: any = b[column];

      // Si estamos ordenando por la categoría, accedemos a la propiedad anidada
      if (column === 'category') {
        valueA = a.category.category;
        valueB = b.category.category;
      }

      if (valueA > valueB) {
        return direction;
      } else if (valueA < valueB) {
        return -direction;
      }
      return 0;
    });

    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  }

  submitOrder() {
    if (this.selectedProducts.length > 0) {
      this._inventoryService.setSelectedProducts(this.selectedProducts);
    }
  }

  showModalEditProduct(id: number) {
    if (this.editProductoModalBootstrap) {
      this.editProductoModalBootstrap.show();
      const producto = this.inventoryList.find((p) => (p.id = id));
      if (producto) {
        this.productoForm.get('product')?.disable();
        this.productoForm.setValue(producto);
      }
    }
  }
}
