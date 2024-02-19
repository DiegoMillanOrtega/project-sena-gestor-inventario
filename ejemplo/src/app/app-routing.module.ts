import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingComponent } from './dashboard/pages/setting/setting.component';
import { InventoryComponent } from './dashboard/pages/inventory/inventory.component';
import { ContainerItemsComponent } from './dashboard/pages/container-items/container-items.component';
import { AddProductComponent } from './dashboard/pages/inventory/add-product/add-product.component';
import { DetailsProductsComponent } from './dashboard/pages/inventory/details-products/details-products.component';
import { CategoryComponent } from './dashboard/pages/inventory/category/category.component';
import { AddCategoryComponent } from './dashboard/pages/inventory/category/add-category/add-category.component';
import { PedidosComponent } from './dashboard/pages/inventory/pedidos/pedidos.component';
import { ListPedidosComponent } from './dashboard/pages/inventory/pedidos/list-pedidos/list-pedidos.component';
import { ClientComponent } from './dashboard/pages/client/client.component';

const routes: Routes = [
  { path: '', component: ContainerItemsComponent},
  { path: 'setting', component: SettingComponent},
  { path: 'inventory', component: InventoryComponent},
  { path: 'inventory/addProduct', component: AddProductComponent},
  { path: 'inventory/details/:productId/:product', component: DetailsProductsComponent},
  { path: 'inventory/pedido/:productId/:product', component: PedidosComponent},
  { path: 'category', component: CategoryComponent},
  { path: 'category/newCategory', component: AddCategoryComponent},
  { path: 'listPedidos', component: ListPedidosComponent},
  { path: 'client', component: ClientComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
