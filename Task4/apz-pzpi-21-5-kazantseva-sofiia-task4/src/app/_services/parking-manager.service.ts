import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../_models/request/register-request";
import {CompanyDTO} from "../_models/response/company-dto";
import {ParkingManagerDTO} from "../_models/response/parking-manager-dto";
import {ManagerRequest} from "../_models/request/manager-request";
import {PaginatedResponse} from "../_models/response/paginated-response";
import {ShortParkingPlaceDTO} from "../_models/response/short-parking-place-dto";
import {ParkingPlaceDTO} from "../_models/response/parking-place-dto";
import {ShortInvoiceDTO} from "../_models/response/short-invoice-dto";
import {InvoiceDTO} from "../_models/response/invoice-dto";
import {UpdatePlaceRequest} from "../_models/request/update-place-request";
import {PlaceRequest} from "../_models/request/place-request";

@Injectable({
  providedIn: 'root'
})
export class ParkingManagerService {

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
    return this.http.post(this.baseUrl + '/parking-manager/register', data, httpOptions);
  }

  getCompanyList() {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<CompanyDTO[]>(this.baseUrl + '/parking-company/search', httpOptions);
  }

  async getManager() {
    await this.getCredentials()

    return await this.http.get<ParkingManagerDTO>(
      this.baseUrl + '/parking-manager/' + this.id, this.httpOptions).toPromise();
  }

  async updateManager(managerRequest: ManagerRequest) {
    await this.getCredentials()
    return await this.http.patch<void>(this.baseUrl + "/parking-manager/" + this.id, managerRequest,
      this.httpOptions).toPromise()
  }

  async deleteManager() {
    await this.getCredentials();

    return await this.http.delete(this.baseUrl + '/parking-manager/' + this.id, this.httpOptions)
      .toPromise();
  }

  async getPlacesByCompany(page: number) {
    await this.getCredentials()

    return await this.http.get<PaginatedResponse<ShortParkingPlaceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + '/parking-places?size=3&page=' + page,
      this.httpOptions).toPromise();
  }

  async getPlace(placeId: number) {
    await this.getCredentials()

    return await this.http.get<ParkingPlaceDTO>(
      this.baseUrl + '/parking-manager/' + this.id + '/parking-places/' + placeId,
      this.httpOptions).toPromise();
  }

  async createPlace(placeRequest: PlaceRequest) {
    await this.getCredentials()

    return await this.http.post<ParkingPlaceDTO>(
      this.baseUrl + '/parking-manager/' + this.id + '/parking-places', placeRequest,
      this.httpOptions).toPromise();
  }

  async updatePlace(placeId: number, placeRequest: UpdatePlaceRequest) {
    await this.getCredentials()

    return await this.http.patch<ParkingPlaceDTO>(
      this.baseUrl + '/parking-manager/' + this.id + '/parking-places/' + placeId, placeRequest,
      this.httpOptions).toPromise();
  }

  async updatePlaceByIOT() {
    await this.getCredentials()

    return await this.http.get<ParkingPlaceDTO>(
      this.iotUrl + '/parking-place/get-data',
      this.httpOptions).toPromise();
  }

  async deletePlace(placeId: bigint) {
    await this.getCredentials()

    return await this.http.delete(this.baseUrl + '/parking-manager/' + this.id + '/parking-places/' + placeId,
      this.httpOptions).toPromise();
  }

  async signInvoice(invoiceId: bigint) {
    await this.getCredentials();

    return await this.http.patch<InvoiceDTO>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/" + invoiceId, null,
      this.httpOptions).toPromise();
  }

  async getInvoice(invoiceId: number) {
    await this.getCredentials();

    return await this.http.get<InvoiceDTO>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/" + invoiceId,
      this.httpOptions).toPromise();
  }

  async getInvoices(page: number) {
    await this.getCredentials();

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices?size=7&page=" + page,
      this.httpOptions).toPromise();
  }

  async searchInvoices(placeNumber: string, page: number) {
    await this.getCredentials();

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/place-number/" + placeNumber
      + '?size=7&page=' + page,
      this.httpOptions).toPromise();
  }

  async getSignedInvoices(placeNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&placeNumber=" + placeNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/signed" + prams,
      this.httpOptions).toPromise();
  }

  async getNotSignedByParkingManagerInvoices(placeNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&placeNumber=" + placeNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/not-signed-by-parking-manager" + prams,
      this.httpOptions).toPromise();
  }

  async getNotSignedByTruckManagerInvoices(placeNumber: string, page: number) {
    await this.getCredentials();

    var prams = "?size=7&page=" + page + "&placeNumber=" + placeNumber;

    return await this.http.get<PaginatedResponse<ShortInvoiceDTO>>(
      this.baseUrl + '/parking-manager/' + this.id + "/invoices/not-signed-by-truck-manager" + prams,
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
      this.baseUrl + '/parking-managers/' + this.id + "/invoices/" + invoiceId + "/export",
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
