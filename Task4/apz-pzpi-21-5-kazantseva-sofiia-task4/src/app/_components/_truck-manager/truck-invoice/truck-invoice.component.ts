import {Component} from '@angular/core';
import {InvoiceDTO} from "../../../_models/response/invoice-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from '../../../_services/util.service';
import {TruckManagerService} from "../../../_services/truck-manager.service";

@Component({
  selector: 'app-truck-invoice',
  templateUrl: './truck-invoice.component.html',
  styleUrl: './truck-invoice.component.css'
})
export class TruckInvoiceComponent {
  protected invoice: InvoiceDTO | undefined;
  protected readonly UtilService = UtilService;

  constructor(private truckManagerService: TruckManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.getInvoice(id);
  }

  protected async signInvoice(id: bigint) {
    try {
      await this.truckManagerService.signInvoice(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    window.location.reload();
  }

  protected async exportInvoice(id: bigint) {
    try {
      await this.truckManagerService.generateInvoice(id).then(res => {
          res.subscribe(
            response => {
              const file = new Blob([response], {type: "application/pdf"});
              const fileUrl = URL.createObjectURL(file);
              window.open(fileUrl);
            },
            error => {
              UtilService.displayError(error, this.router)
            }
          )
        }
      )
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  private async getInvoice(id: number) {
    try {
      this.invoice = await this.truckManagerService.getInvoice(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
