import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TruckManagerService} from "../../../_services/truck-manager.service";
import {Router} from "@angular/router";
import {ItemRequest} from "../../../_models/request/item-request";
import {InvoiceRequest} from "../../../_models/request/invoice-request";
import {UtilService} from "../../../_services/util.service";

@Component({
  selector: 'app-invoice-update',
  templateUrl: './invoice-update.component.html',
  styleUrl: './invoice-update.component.css'
})
export class InvoiceUpdateComponent {

  invoiceForm: FormGroup;

  constructor(private fb: FormBuilder, private truckManagerService: TruckManagerService, private router: Router) {
    this.invoiceForm = this.fb.group({
      truckNumber: ['', Validators.required],
      placeNumber: ['', Validators.required],
      description: [''],
      items: this.fb.array([])
    });
  }

  get items(): FormArray {
    return this.invoiceForm.get('items') as FormArray;
  }

  ngOnInit(): void {
    this.addItem();
  }

  addItem(): void {
    const itemForm = this.fb.group({
      name: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      amount: [0, [Validators.required, Validators.min(0)]],
      weight: [0, [Validators.required, Validators.min(0)]]
    });
    this.items.push(itemForm);
  }

  removeItem(index: number): void {
    this.items.removeAt(index);
  }

  protected async onSubmit() {
    if (this.invoiceForm.valid) {
      const formValue = this.invoiceForm.value;
      const items: ItemRequest[] = formValue.items.map((item: any) =>
        new ItemRequest(item.name, item.weight, item.price, item.amount)
      );

      const invoiceRequest = new InvoiceRequest(
        "LOADING_INVOICE",
        formValue.truckNumber,
        formValue.placeNumber,
        items,
        formValue.description
      );

      try {
        await this.truckManagerService.createInvoice(invoiceRequest);
      } catch (error) {
        UtilService.displayError(error, this.router)
      }

      await this.router.navigate(['/truck-manager/invoices']);
    }
  }
}
