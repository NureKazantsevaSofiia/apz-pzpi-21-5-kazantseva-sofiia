import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {AdminDTO} from "../_models/response/admin-dto";
import {AdminRequest} from "../_models/request/admin-request";
import {TruckManagerDTO} from "../_models/response/truck-manager-dto";
import {ParkingManagerDTO} from "../_models/response/parking-manager-dto";
import {DatabaseHistoryDTO} from "../_models/response/database-history-dto";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8080/api';
  private iotUrl = 'http://localhost:5000';
  private token: string | undefined;
  private id: string | undefined;
  private httpOptions: { headers: HttpHeaders } | undefined;

  constructor(private http: HttpClient, private loginService: LoginService, private router: Router) {
  }

  async getAdmin() {
    await this.getCredentials()

    return await this.http.get<AdminDTO>(
      this.baseUrl + '/admins/' + this.id, this.httpOptions).toPromise();
  }

  async addAdmin(email: string) {
    await this.getCredentials()

    return await this.http.post(this.baseUrl + '/admins/' + this.id + "/add/" + email,
      null, this.httpOptions).toPromise();
  }

  async approveAdmin(newAdminId: bigint) {
    await this.getCredentials()

    return this.http.post(this.baseUrl + '/admins/' + this.id +
      "/approve/" + newAdminId, null, this.httpOptions);
  }

  async declineAdmin(newAdminId: bigint) {
    await this.getCredentials()

    return this.http.post(this.baseUrl + '/admins/' + this.id +
      "/decline/" + newAdminId, null, this.httpOptions);
  }

  async updateAdmin(updatedAdmin: AdminRequest) {
    await this.getCredentials()

    return await this.http.patch(this.baseUrl + '/admins/' + this.id, updatedAdmin, this.httpOptions).toPromise();
  }

  async getApprovedAdmins() {
    await this.getCredentials()

    return await this.http.get<AdminDTO[]>(this.baseUrl + '/admins/approved', this.httpOptions).toPromise();
  }

  async getNotApprovedAdmins() {
    await this.getCredentials()

    return await this.http.get<AdminDTO[]>(this.baseUrl + '/admins/not-approved', this.httpOptions).toPromise();
  }

  async getTruckManagers() {
    await this.getCredentials()

    return await this.http.get<TruckManagerDTO[]>(this.baseUrl + '/admins/' + this.id +
      "/truck-managers", this.httpOptions).toPromise();
  }

  async getParkingManagers() {
    await this.getCredentials()

    return await this.http.get<ParkingManagerDTO[]>(this.baseUrl + '/admins/' + this.id +
      "/parking-managers", this.httpOptions).toPromise();
  }

  async deleteTruckManager(truckManagerId: bigint) {
    await this.getCredentials()

    return await this.http.delete(this.baseUrl + '/admins/' + this.id +
      "/truck-managers/" + truckManagerId, this.httpOptions).toPromise();
  }

  async deleteParkingManager(parkingManagerId: bigint) {
    await this.getCredentials()

    return await this.http.delete(this.baseUrl + '/admins/' + this.id +
      "/parking-managers/" + parkingManagerId, this.httpOptions).toPromise();
  }

  async connect(host: string) {
    await this.getCredentials()

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "text"
      })
    };

    return await this.http.post(this.iotUrl + '/connect/' + host,
      null, this.httpOptions).toPromise();
  }

  async getWeekDatabaseHistory() {
    await this.getCredentials()

    return await this.http.get<DatabaseHistoryDTO[]>(this.baseUrl + '/admins/' + this.id +
      "/db/history", this.httpOptions).toPromise();
  }

  async generateWeekDatabaseReport() {
    await this.getCredentials();

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "text",
        Accept: "application/pdf",
        'Authorization': 'Bearer ' + this.token
      }),
      responseType: 'arraybuffer' as const
    };

    return this.http.get(this.baseUrl + "/admins/" + this.id + "/db/week-report",
      httpOptions)
  }

  async generateMonthDatabaseReport() {
    await this.getCredentials();

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "text",
        Accept: "application/pdf",
        'Authorization': 'Bearer ' + this.token
      }),
      responseType: 'arraybuffer' as const
    };

    return this.http.get(this.baseUrl + "/admins/" + this.id + "/db/month-report",
      httpOptions)
  }

  async exportDatabase() {
    await this.getCredentials();

    let httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "text",
        Accept: "application/zip",
        'Authorization': 'Bearer ' + this.token
      }),
      responseType: 'arraybuffer' as const
    };

    return this.http.get(this.baseUrl + "/admins/" + this.id + "/db/export",
      httpOptions);
  }

  async importDatabase(file: File) {
    await this.getCredentials();

    const formData = new FormData();
    formData.append('file', file);

    let httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + this.token
      })
    };

    return await this.http.post(this.baseUrl + "/admins/" + this.id + "/db/import", formData,
      httpOptions).toPromise();
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
