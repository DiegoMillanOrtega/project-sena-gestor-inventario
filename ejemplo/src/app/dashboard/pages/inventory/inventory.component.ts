import { Component, OnInit, inject } from '@angular/core';
import { InventoryService } from '../../../service/inventory.service';
import { Inventory } from '../../../model/inventory.model';
import Swal from 'sweetalert2';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../model/category.model';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.css',
})
export class InventoryComponent implements OnInit {
  private _inventoryService = inject(InventoryService);
  private categoryService = inject(CategoryService);

  inventoryList: Inventory[] = [];
  categoryList: Category[] = [];
  productName: string = '';
  productCategory: string = '';
  loading: boolean = true;

  deleteProduct(id: number) {
    Swal.fire({
      title: 'Estás seguro?',
      text: 'No podrás revertir esto!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this._inventoryService.deleteProductId(id).subscribe(
          (response) => {
            console.log(response),
              this._inventoryService.getInventoryList().subscribe(
                (data) => (this.inventoryList = data),
                (error) =>
                  console.error(
                    'Error al obtener la lista de inventario',
                    error
                  )
              );
          },
          (error) => console.error(error)
        );
        Swal.fire({
          title: 'Eliminado!',
          text: 'El producto ha sido eliminado',
          icon: 'success',
        });
      }
    });
  }

  ngOnInit(): void {
    this._inventoryService.getInventoryList().subscribe(
      (data) => (this.inventoryList = data),
      (error) => console.error('Error al obtener la lista de inventario', error)
    );
    this.categoryService.getCategoryList().subscribe(
      (data) => {
        this.categoryList = data;
        console.log('Se obtuvo las categorias!!', data);
      },
      (error) => console.error('Error al obtener la lista de categorias', error)
    );

    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }

  buscarProductos() {
    this.loading = true;

    this._inventoryService
      .searchProductByName(this.productName)
      .subscribe((data) => (this.inventoryList = data));
    this.productName = '';

    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }

  buscarProductosByCategory(category: string) {
    this.loading = true;
    this.productCategory = category;
    this._inventoryService
      .searchByCategory(category)
      .subscribe((data) => (this.inventoryList = data));
    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }
}
