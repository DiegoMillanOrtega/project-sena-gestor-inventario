import { Component, OnInit, inject } from '@angular/core';
import { ClientService } from '../../../service/client.service';
import { Client } from '../../../model/client.model';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrl: './client.component.css'
})
export class ClientComponent implements OnInit{

  private serviceClient = inject(ClientService)

  clientsList: Client[] = [];

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(){
    this.serviceClient.findAllClients().subscribe(
      paramas => this.clientsList = paramas,
      error => console.error('error al cargar lista de cliente ', error)
    )
  }
}
