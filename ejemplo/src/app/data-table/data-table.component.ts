import { AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css'
})
export class DataTableComponent implements AfterViewInit{

  @Input() data: any[] = [];
  @Input() columns: { key: string; title: string }[] = [];
  @Input() keyId: string = '';
  @Input() tableStyle: string = 'table';
  @Input() modalTitle: string = 'Modal Title';
  @Input() modalElement!: ElementRef;
  @Input() formStructure!: any[];
  @Input() formGroup!: FormGroup;
  @Input() buttons?: {
    icon: string;
    id: string
    class?: string;
  }[]

  @Output() dataExport = new EventEmitter<any>();
  @Output() totalExport = new EventEmitter<number>();
  @Output() bottonClicked: EventEmitter<{btn: string, data?:any}> = new EventEmitter();

  dataFormGroup: any[] = [];

  @ViewChild('modalElement', { static: false }) modalRef!: ElementRef;

  private modalDetalleProdInst!: Modal;
  private modalConfirmacionInstance!: Modal;

  id!: number
  dataSelected: any = {};
  total: number = 0

  ngAfterViewInit() {
    this.modalDetalleProdInst = new Modal(this.modalRef.nativeElement);
  }

  onButtonClick(boton: string, item: any) {
    if (boton === 'eliminar') {
      const index: number = this.data.indexOf(item);
      const dataToEmit = {
        btn: boton,
        data: index
      }
      this.bottonClicked.emit(dataToEmit)
      return  
    }
    const dataToEmit = {
      btn: boton,
      data: item
    }
    this.bottonClicked.emit(dataToEmit)
  }

  editProduct(id: number) {
    document.body.appendChild(this.modalRef.nativeElement)
    const data = this.data.find((d) => d.id === id);   
    if (data) {
      this.id = id
      this.dataFormGroup = [data];
      this.dataSelected = data;
      this.dataExport = data;
      console.log(this.dataExport);
      
    }
    this.modalDetalleProdInst.show();
  }
  
 
  updateProduct() {
    const index = this.data.indexOf(this.dataSelected)
    console.log(index);
    if (index !== -1) {
      if (this.dataSelected.stock < this.formGroup.get('stock')?.value) {
        this.calcularTotales(true) 
      } else if (this.dataSelected.stock > this.formGroup.get('stock')?.value) {
        this.calcularTotales(false)
      }
       this.data[index] = this.formGroup.value
       this.modalDetalleProdInst.hide()
       
      }
  }

  closeModal() {
    this.modalDetalleProdInst.hide();
    document.body.removeChild(this.modalRef.nativeElement)
    console.log(this.formGroup.value);
    
  }

  isObject(value: any): boolean {
    return value && typeof value === 'object' && !Array.isArray(value);
  }

  calcularTotales(vmodo: boolean) {
    if (vmodo) {
      const valor = Number(this.formGroup.get('stock')?.value * this.formGroup.get('price')?.value)
      this.total += valor
      this.totalExport.emit(this.total)
    } else {
      const valor = Number(this.formGroup.get('stock')?.value * this.formGroup.get('price')?.value)
      this.total -= valor
      this.totalExport.emit(this.total)
    }
  }
}
