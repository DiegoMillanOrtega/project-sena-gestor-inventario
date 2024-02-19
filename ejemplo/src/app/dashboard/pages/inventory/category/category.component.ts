import { Component, OnInit, inject } from '@angular/core';
import { CategoryService } from '../../../../service/category.service';
import { Category } from '../../../../model/category.model';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit{

  private categoryService = inject(CategoryService);

  categoryForm: FormGroup;
  CategoryList: Category[] = [];
  countsByCategory: Object[] = [];
  category: string = '';

  constructor(private form: FormBuilder) {
    this.categoryForm = this.form.group({
      id: [''],
      category: ['']
    })
  }

  ngOnInit(): void {
    this.loadCategory();
  }

  loadCategory() {
    this.categoryService.getCategoryList().subscribe(
      params => {
        console.log('Se recibio la lista!!', params);
        this.CategoryList = params;
      },
      error => console.error('error al obtener la lista :(', error)
    )
  }


  buscarCategoria() {
    this.categoryService.searchCategoryByName(this.category).subscribe(
      response => {
        console.log('Se pudo encontrar la categoria!!');
        this.CategoryList = response;
      },
      error => console.error('error al encontrar la categoria', error)
    )
  }
  
  editarCategoria(id: number | string) {
    this.categoryService.searchCategoryById(id).subscribe(
      response => {
        console.log('Categoria: ',response);
        this.categoryForm.patchValue(response);
      },
      error => console.error('error al cargar la categoria ', error)
    );
    
    Swal.fire({
      showCancelButton: true,
      cancelButtonColor: "#d33",
      confirmButtonColor: "#4CAF50",
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Actualizar',
      html: '<h2>Editar categoría</h2>'+
            
      '<div class="form-floating mt-4">' +
      '<input type="search" class="form-control" id="floatingInputGroup2" required>' +
      '<label for="floatingInputGroup2">Nombre de la categoría:</label>' +
      '</div>',
      focusConfirm: false,
      preConfirm: () => {
        const categoryInput = Swal.getPopup()?.querySelector('#floatingInputGroup2') as HTMLInputElement;

        if (!categoryInput.value) {
          Swal.showValidationMessage('Por favor, complete todos los campos');
          return;
        } else {
          this.categoryForm.get('category')?.setValue(categoryInput.value);
        }
        
      },
    }).then(result => {
      if (result.isConfirmed) {
        this.categoryService.saveCategory(this.categoryForm.value).subscribe(
          response => {
            console.log('categoria actualizada', response);
            this.loadCategory();
            Swal.fire({
              title: "Actualizado!",
              text: "El producto ha sido actualizado",
              icon: "success"
            });
        },
          error => {
            console.error('error al actualizar categoria', error);
            Swal.fire({
              title: "Error al actualizar!",
              text: "El producto no ha sido actualizado",
              icon: "error"
            });
          }
        );
        
      }
    })
  }

  eliminarCategoria(id: string | number) {

    Swal.fire({
      title: "Estás seguro?",
      text: "No podrás revertir esto!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Si, eliminar!",
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoryService.deleteCategory(id).subscribe(
          response => {
            console.log(response);
            Swal.fire({
              title: "Eliminado!",
              text: "El producto ha sido eliminado",
              icon: "success"
            });
            this.loadCategory();
          },
          error => {
            console.error(error);
            Swal.fire({
              title: "¡Error!",
              text: "El producto no se pudo eliminar",
              icon: "error"
            });
          }
        );
        
      }
    });
  }
}
