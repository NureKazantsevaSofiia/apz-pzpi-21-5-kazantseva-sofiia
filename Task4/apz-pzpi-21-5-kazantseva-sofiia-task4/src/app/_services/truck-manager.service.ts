import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../_models/request/register-request";
import {CompanyDTO} from "../_models/response/company-dto";
import {ManagerRequest} from "../_models/request/manager-request";
import {TruckManagerDTO} from "../_models/response/truck-manager-dto";
import {PaginatedResponse} from "../_models/response/paginated-response";
import {ParkingPlaceDTO} from "../_models/response/parking-place-dto";
import {InvoiceDTO} from "../_models/response/invoice-dto";
import {ShortInvoiceDTO} from "../_models/response/short-invoice-dto";
import {TruckDTO} from "../_models/response/truck-dto";
import {InvoiceRequest} from "../_models/request/invoice-request";
import {UpdateTruckRequest} from "../_models/request/update-truck-request";
import {TruckRequest} from "../_models/request/truck-request";
import {ShortTruckDTO} from "../_models/response/short-truck-dto";

@Injectable({
  providedIn: 'root'
})
export class TruckManagerService {

  private baseUrl = 'http://localhost:8080/api';
  private iotUrl = 'http://localhost:5000';

  private token: string | undefined;
  private id: string | undefined;
  private httpOptions: { headers: HttpHeaders } | undefined;

  constructor(private http: HttpClient, private loginService: LoginService, private router: Router) {
  }

  register(data: RegisterRequest) {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post(this.baseUrl + '/truck-manager/register', data, httpOptions);
  }

  getCompanyList() {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<CompanyDTO[]>(this.baseUrl + '/truck-company/search', httpOptions);
  }

  async getManager() {
    await this.getCredentials()

    return await this.http.get<TruckManagerDTO>(
      this.baseUrl + '/truck-manager/' + this.id, this.httpOptions).toPromise();
  }

  async updateManager(managerRequest: ManagerRequest) {
    await this.getCredentials()

    return await this.http.patch<void>(this.baseUrl + "/truck-manager/" + this.id, managerRequest,
      this.httpOptions).toPromise()
  }

  async deleteManager() {
    await this.getCredentials();

    return await this.http.delete(this.baseUrl + '/truck-manager/' + this.id, this.httpOptions)
      .toPromise();
  }

  async getTrucksByCompany(page: number) {
    await this.getCredentials()

    return await this.http.get<PaginatedResponse<ShortTruckDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + '/trucks?size=3&page=' + page,
      this.httpOptions).toPromise();
  }

  async getTruck(truckId: number) {
    await this.getCredentials()

    return await this.http.get<TruckDTO>(
      this.baseUrl + '/truck-manager/' + this.id + '/trucks/' + truckId,
      this.httpOptions).toPromise();
  }

  async createTruck(truckRequest: TruckRequest) {
    await this.getCredentials()

    return await this.http.post<TruckDTO>(
      this.baseUrl + '/truck-manager/' + this.id + '/trucks', truckRequest,
      this.httpOptions).toPromise();
  }

  async updateTruck(truckId: number, truckRequest: UpdateTruckRequest) {
    await this.getCredentials()

    return await this.http.patch<ParkingPlaceDTO>(
      this.baseUrl + '/truck-manager/' + this.id + '/trucks/' + truckId, truckRequest,
      this.httpOptions).toPromise();
  }

  async updateTruckByIOT() {
    await this.getCredentials()

    return await this.http.get<TruckDTO>(
      this.iotUrl + '/truck/get-data',
      this.httpOptions).toPromise();
  }

  async deleteTruck(truckId: bigint) {
    await this.getCredentials()

    return await this.http.delete(this.baseUrl + '/truck-manager/' + this.id + '/trucks/' + truckId,
      this.httpOptions).toPromise();
  }

  async createInvoice(invoiceRequest: InvoiceRequest) {
    await this.getCredentials();

    return await this.http.post<InvoiceDTO>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices", invoiceRequest,
      this.httpOptions).toPromise();
  }

  async signInvoice(invoiceId: bigint) {
    await this.getCredentials();

    return await this.http.patch<InvoiceDTO>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/" + invoiceId, null,
      this.httpOptions).toPromise();
  }

  async getInvoice(invoiceId: number) {
    await this.getCredentials();

    return await this.http.get<InvoiceDTO>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/" + invoiceId,
      this.httpOptions).toPromise();
  }

  async getInvoices(page: number) {
    await this.getCredentials();

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices?size=7&page=" + page,
      this.httpOptions).toPromise();
  }

  async searchInvoices(truckNumber: string, page: number) {
    await this.getCredentials();

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/truck-number/" + truckNumber
      + '?size=7&page=' + page,
      this.httpOptions).toPromise();
  }

  async getSignedInvoices(truckNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&truckNumber=" + truckNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/signed" + prams,
      this.httpOptions).toPromise();
  }

  async getNotSignedByParkingManagerInvoices(truckNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&truckNumber=" + truckNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/not-signed-by-parking-manager" + prams,
      this.httpOptions).toPromise();
  }

  async getNotSignedByTruckManagerInvoices(truckNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&truckNumber=" + truckNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/truck-manager/' + this.id + "/invoices/not-signed-by-truck-manager" + prams,
      this.httpOptions).toPromise();
  }

  async generateInvoice(invoiceId: bigint) {
    await this.getCredentials();

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "text",
        Accept: "application/pdf",
        'Authorization': 'Bearer ' + this.token
      }),
      responseType: 'arraybuffer' as const
    };

    return this.http.get(
      this.baseUrl + '/truck-managers/' + this.id + "/invoices/" + invoiceId + "/export",
      httpOptions)
  }

  private async getCredentials() {
    this.token = this.loginService.getToken()
    this.id = this.loginService.getUserId()

    if (!this.token || !this.id) {
      await LoginService.logout(this.router);
      throw new Error("Something went wrong!")
    }

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.token
      })
    };
  }
}
