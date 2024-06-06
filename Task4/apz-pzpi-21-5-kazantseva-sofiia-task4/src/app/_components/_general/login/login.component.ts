import {Component} from '@angular/core';
import {LoginService} from "../../../_services/login.service";
import {Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private loginService: LoginService, private router: Router) {
  }

  protected async login(email: string, password: string) {
    try {
      let loginResponse = await this.loginService.login(email, password);
      let token = loginResponse?.token!;
      let id = loginResponse?.id!;
      let role = loginResponse?.role!;
      await this.redirectPage(token, id, role.toLowerCase());
    } catch (e) {
      UtilService.displayError(e, this.router)
    }
  }

  private async redirectPage(token: string, id: bigint, role: string) {
    if (!token || !id) {
      await LoginService.logout(this.router)
    }

    if (role == 'truck_manager') {
      this.loginService.setCredentials(token, id)
      await this.router.navigate(['/truck-manager']);
    } else if (role == 'parking_manager') {
      this.loginService.setCredentials(token, id)
      await this.router.navigate(['/parking-manager']);
    } else if (role == 'admin' || role == 'chief_admin') {
      this.loginService.setCredentials(token, id)
      await this.router.navigate(['/admin-profile']);
    } else {
      await this.router.navigate(['/login']);
    }
  }
}
