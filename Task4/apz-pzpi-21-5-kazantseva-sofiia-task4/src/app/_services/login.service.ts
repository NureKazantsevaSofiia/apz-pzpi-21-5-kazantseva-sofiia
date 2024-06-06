import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";
import {LoginResponse} from "../_models/response/login-response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  public static isAuthorize(): boolean {
    let token = localStorage.getItem('token');
    let expireDate = localStorage.getItem("expires_at")

    if (token == null || expireDate == null) {
      return false;
    }

    if (Date.now() > Date.parse(expireDate)) {
      localStorage.clear()
      return false;
    }

    return true;
  }

  public static isTruckManager(): boolean {
    if (!this.isAuthorize()) {
      return false;
    }

    let role = this.getRole();

    if (!role) {
      return false
    }

    return role.toLowerCase() == 'truck_manager';
  }

  public static isParkingManager(): boolean {
    if (!this.isAuthorize()) {
      return false;
    }

    let role = this.getRole();

    if (!role) {
      return false
    }

    return role.toLowerCase() == 'parking_manager';
  }

  public static isManager(): boolean {
    if (!this.isAuthorize()) {
      return false;
    }

    let role = this.getRole();

    if (!role) {
      return false
    }

    return role.toLowerCase() == 'truck_manager' || role.toLowerCase() == 'parking_manager';
  }

  public static isAdmin(): boolean {
    if (!this.isAuthorize()) {
      return false;
    }

    let role = this.getRole();

    if (!role) {
      return false
    }

    return role.toLowerCase() == 'admin' || role.toLowerCase() == 'chief_admin';
  }

  public static isChiefAdmin(): boolean {
    if (!this.isAuthorize()) {
      return false;
    }

    let role = this.getRole();

    if (!role) {
      return false
    }

    return role.toLowerCase() == 'chief_admin';
  }

  public static async logout(router: Router) {
    localStorage.clear();
    await router.navigate(["/login"])
  }

  private static getRole(): string | null {
    let token = localStorage.getItem('token');

    if (token == undefined) {
      return null;
    }

    let decodedToken: { [key: string]: string };

    decodedToken = jwtDecode(token);

    return decodedToken['role'];
  }

  public async login(email: string, password: string) {
    let httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + btoa(email + ':' + password)
      })
    };

    return await this.http.post<LoginResponse>(this.baseUrl +
      '/api/login', null, httpOptions).toPromise();
  }

  public setCredentials(token: string, id: bigint) {
    localStorage.clear()
    this.setToken(token)
    this.setUserId(id)
  }

  public getToken(): string {
    return localStorage.getItem('token') ?? '';
  }

  public getUserId(): string {
    return localStorage.getItem('id') ?? '';
  }

  private setToken(token: string) {
    let t = JSON.parse(atob(token.split('.')[1]));
    let expireDate = new Date(t.exp * 1000);
    localStorage.setItem('token', token);
    localStorage.setItem('expires_at', expireDate.toISOString())
  }

  private setUserId(id: bigint) {
    localStorage.setItem('id', id.toString());
  }
}
