import {Component} from '@angular/core';
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {InvoiceDTO} from "../../../_models/response/invoice-dto";

@Component({
  selector: 'app-invoice',
  templateUrl: './parking-invoice.component.html',
  styleUrl: './parking-invoice.component.css'
})
export class ParkingInvoiceComponent {
  protected invoice: InvoiceDTO | undefined;
  protected readonly UtilService = UtilService;

  constructor(private parkingManagerService: ParkingManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.getInvoice(id);
  }

  protected async signInvoice(id: bigint) {
    try {
      await this.parkingManagerService.signInvoice(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    window.location.reload();
  }

  protected async exportInvoice(id: bigint) {
    try {
      await this.parkingManagerService.generateInvoice(id).then(res => {
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
      this.invoice = await this.parkingManagerService.getInvoice(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
