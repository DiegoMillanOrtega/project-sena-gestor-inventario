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
import { AddClientComponent } from './dashboard/pages/client/add-client/add-client.component';
import { DetailsClientComponent } from './dashboard/pages/client/details-client/details-client.component';
import { DataTableComponent } from './data-table/data-table.component';

const routes: Routes = [
  { path: '', component: ContainerItemsComponent, data: {title: 'Inicio'}},
  { path: 'setting', component: SettingComponent, data: {title: 'Configuraciones'}},
  { path: 'inventory', component: InventoryComponent, data: {title: 'Inventario'}},
  { path: 'inventory/addProduct', component: AddProductComponent, data: {title: 'Añadir Producto'}},
  { path: 'inventory/details/:productId/:product', component: DetailsProductsComponent, data: {title: 'Detalle Producto'}},
  { path: 'inventory/pedido/:productId/:product', component: PedidosComponent, data: {title: 'Pedido'}},
  { path: 'category', component: CategoryComponent, data: {title: 'Categorias'}},
  { path: 'category/newCategory', component: AddCategoryComponent, data: {title: 'Nueva Categoria'}},
  { path: 'inventory/listPedidos', component: ListPedidosComponent, data: {title: 'Lista de Pedidos'}},
  { path: 'inventory/listPedidos/pedido', component: PedidosComponent, data: {title: 'Pedido'}},
  { path: 'client', component: ClientComponent, data: {title: 'Clientes'}},
  { path: 'client/addClient', component: AddClientComponent, data: {title: 'Añadir Cliente'}},
  { path: 'client/details/:id/:client', component: DetailsClientComponent, data: {title: 'Detalle Cliente'}},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
