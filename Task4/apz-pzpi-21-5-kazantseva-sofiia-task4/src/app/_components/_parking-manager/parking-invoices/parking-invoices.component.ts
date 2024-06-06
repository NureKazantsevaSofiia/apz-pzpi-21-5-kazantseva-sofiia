import {Component} from '@angular/core';
import {UtilService} from "../../../_services/util.service";
import {PaginatedResponse} from "../../../_models/response/paginated-response";
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {Router} from "@angular/router";
import {ShortInvoiceDTO} from "../../../_models/response/short-invoice-dto";

@Component({
  selector: 'app-parking-invoices',
  templateUrl: './parking-invoices.component.html',
  styleUrl: './parking-invoices.component.css'
})
export class ParkingInvoicesComponent {

  protected readonly UtilService = UtilService;
  protected invoices: PaginatedResponse<ShortInvoiceDTO> | undefined;
  protected currentPage: number = 0;
  protected totalPages: number = 0;
  protected filter: number = 0;
  protected searchField: string = '';


  constructor(private managerService: ParkingManagerService, private router: Router) {
  }

  ngOnInit() {
    this.getAllInvoices();
  }

  protected async openInvoice(invoiceId: bigint) {
    await this.router.navigate(['/parking-manager/invoice', invoiceId]);
  }


  protected async changePage(number: number) {
    if (number > 0 && number <= this.totalPages) {
      this.currentPage = number - 1;
      await this.getAllInvoices();
    }
  }

  protected async getAllInvoices() {
    this.filter = 0;
    let page = this.currentPage - 1;
    this.clearSelecting()
    document.getElementById("1btn")!.classList.add('selected')
    try {
      if (this.searchField == '') {
        this.invoices = await this.managerService.getInvoices(page);
      } else {
        this.invoices = await this.managerService.searchInvoices(this.searchField, page);
      }
      this.currentPage = this.invoices?.number! + 1;
      this.totalPages = this.invoices?.totalPages!;
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async getNotSignedByParkInvoices() {
    this.filter = 1;
    let page = this.currentPage - 1;
    this.clearSelecting()
    document.getElementById("2btn")!.classList.add('selected')
    try {
      this.invoices = await this.managerService.getNotSignedByParkingManagerInvoices(this.searchField, page);
      this.currentPage = this.invoices?.number! + 1;
      this.totalPages = this.invoices?.totalPages!;
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async getNotSignedByTrInvoices() {
    this.filter = 2;
    let page = this.currentPage - 1;
    this.clearSelecting()
    document.getElementById("3btn")!.classList.add('selected')
    try {
      this.invoices = await this.managerService.getNotSignedByTruckManagerInvoices(this.searchField, page);
      this.currentPage = this.invoices?.number! + 1;
      this.totalPages = this.invoices?.totalPages!;
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async getSignedInvoices() {
    this.filter = 3;
    let page = this.currentPage - 1;
    this.clearSelecting()
    document.getElementById("4btn")!.classList.add('selected')
    try {
      this.invoices = await this.managerService.getSignedInvoices(this.searchField, page);
      this.currentPage = this.invoices?.number! + 1;
      this.totalPages = this.invoices?.totalPages!;
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async search(value: string) {
    this.searchField = value;
    switch (this.filter) {
      case 0: {
        await this.getAllInvoices();
        break;
      }
      case 1: {
        await this.getNotSignedByParkInvoices();
        break;
      }
      case 2: {
        await this.getNotSignedByTrInvoices();
        break;
      }
      case 3: {
        await this.getSignedInvoices();
        break;
      }
      default: {
        await this.getAllInvoices();
        break;
      }
    }
  }

  protected async clear() {
    this.searchField = '';
    let input: HTMLInputElement;
    input = document.getElementById("placeNumber") as HTMLInputElement;
    input.value = ""
    switch (this.filter) {
      case 0: {
        await this.getAllInvoices();
        break;
      }
      case 1: {
        await this.getNotSignedByParkInvoices();
        break;
      }
      case 2: {
        await this.getNotSignedByTrInvoices();
        break;
      }
      case 3: {
        await this.getSignedInvoices();
        break;
      }
      default: {
        await this.getAllInvoices();
        break;
      }
    }
  }

  private clearSelecting() {
    let button1 = document.getElementById("1btn");
    let button2 = document.getElementById("2btn");
    let button3 = document.getElementById("3btn");
    let button4 = document.getElementById("4btn");

    let elems = [button1, button2, button3, button4]

    for (const element of elems) {
      if (element!.classList.contains("selected")) {
        element!.classList.remove("selected");
      }
    }
  }
}
