import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClientService } from '../../../../service/client.service';
import Swal from 'sweetalert2';
import { AlertsService } from '../../../../alerts/alerts.service';
import { Router } from '@angular/router';
import { ToastsService } from '../../../../service/toasts.service';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrl: './add-client.component.css',
})
export class AddClientComponent {
  private serviceCliente = inject(ClientService);
  private alerts = inject(AlertsService);
  private route = inject(Router);
  private toast = inject(ToastsService)

  newClient: FormGroup;

  constructor(private form: FormBuilder) {
    this.newClient = this.form.group({
      id: [''],
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      gender: [''],
      address: [''],
      email: ['',[Validators.required, Validators.email]],
      phoneNumber: [''],
    });
  }
  addCliente() {
    if (this.newClient.valid) {
      const capitalizedValues = {
        ...this.newClient.value,
        lastName: this.capitalize(this.newClient.value.lastName),
        name: this.capitalize(this.newClient.value.name),
      };

      this.serviceCliente.addClient(capitalizedValues).subscribe(
        (response) => {
          this.toast.showToast('Cliente guardado', 'El Cliente se ha guardado correctamente', 'success', 4000)
          this.route.navigate(['/client']);
        },
        (error) =>
          this.alerts.mostrarMensajeError('Error al guardar el cliente')
      );
    }
  }

  capitalize(value: string): string {
    return value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
  }
}
