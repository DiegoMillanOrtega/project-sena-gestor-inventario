import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Inventory } from '../model/inventory.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private apiUrl: string = 'http://localhost:8080/inventory';
  private http = inject(HttpClient);
  private selectedProducts: Inventory[] = [];

  getInventoryList(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/getInventory`)
  }

  getProductId(id: number): Observable<Inventory> {
    return this.http.get<Inventory>(`${this.apiUrl}/getProductById/${id}`, { responseType: 'json' });
  }
  
  getOutOfStockProducts(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/getOutOfStockProducts`)
  }

  searchProductByName(product: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search?product=${product}`);
  }

  searchProductByNameOutOfStock(product: string): Observable<Inventory[]> {
    console.log(`${this.apiUrl}/searchOutOfStock/${product}`);
    return this.http.get<Inventory[]>(`${this.apiUrl}/searchOutOfStock/${product}`);
  }

  searchByCategory(category: string): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/searchCategory/${category}`);
  }

  searchByCategoryOutOfStock(category: string): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/getByProductsByCategoryOutOfStock/${category}`);
  }

  saveProduct(product: Inventory): Observable<any> {
    return this.http.post(`${this.apiUrl}/postProduct`, product, {responseType: 'text'})
  }

  deleteProductId(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteProduct/${id}`, {responseType: 'text'})
  }

  getSelectedProducts() {
    return this.selectedProducts;
  }

  setSelectedProducts(selectedProducts: Inventory[]) {
    if (selectedProducts.length > 0) {
      this.selectedProducts = selectedProducts;
    }
  }
}
