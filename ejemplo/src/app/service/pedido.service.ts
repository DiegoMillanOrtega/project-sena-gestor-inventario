import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Pedido } from '../model/pedido.model';
import { PedidoDetalle } from '../model/pedidoDetalle.model';
import { Inventory } from '../model/inventory.model';
import { PedidoDTO } from '../model/pedidoDTO.model';


@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  private apiUrl: string = 'http://localhost:8080/pedido'
  private http = inject(HttpClient);

  public getPedidoList(): Observable<PedidoDTO[]> {
    return this.http.get<PedidoDTO[]>(`${this.apiUrl}/getPedidos`);
  }

  public savePedido(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(`${this.apiUrl}/savePedido`, pedido );
}

}
