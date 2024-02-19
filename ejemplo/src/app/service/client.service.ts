import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Client } from '../model/client.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private apiUrl: string = 'http://localhost:8080/client';
  private http = inject(HttpClient);

  findAllClients(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/getAllClient`);
  }
  
  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}/saveClient`, client);
  }
  
  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteClient/${id}`);
  }
  
  updateClient(client: Client): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/updateClient`, client);
  }
  
  findClientById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/getClientById/${id}`);
  }
}
