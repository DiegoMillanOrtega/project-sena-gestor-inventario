import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ClientService } from '../../../../service/client.service';
import Swal from 'sweetalert2';
import { AlertsService } from '../../../../alerts/alerts.service';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrl: './add-client.component.css',
})
export class AddClientComponent {
  private serviceCliente = inject(ClientService);
  private alerts = inject(AlertsService);

  newClient: FormGroup;

  constructor(private form: FormBuilder) {
    this.newClient = this.form.group({
      id: [''],
      name: [''],
      lastName: [''],
      gender: [''],
      address: [''],
      email: [''],
      phoneNumber: [''],
    });
  }
  addCliente() {
    this.serviceCliente.addClient(this.newClient.value).subscribe(
      (response) => {
        this.alerts.mostrarMensajeExito(
          '¡Guardado!',
          'El cliente guardado correctamente.'
        );
        this.newClient.reset();
      },
      (error) => this.alerts.mostrarMensajeError('Error al guardar el cliente')
    );
  }
}
