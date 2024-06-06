import {Component} from '@angular/core';
import {AdminService} from "../../../_services/admin.service";
import {Router} from "@angular/router";
import {AdminRequest} from "../../../_models/request/admin-request";
import {UtilService} from "../../../_services/util.service";

@Component({
  selector: 'app-admin-update-profile',
  templateUrl: './admin-update-profile.component.html',
  styleUrl: './admin-update-profile.component.css'
})
export class AdminUpdateProfileComponent {
  constructor(private adminService: AdminService, private router: Router) {
  }

  protected async updateAdmin(avatar: string, firstName: string, lastName: string, password: string) {
    try {
      await this.adminService.updateAdmin(new AdminRequest(avatar, firstName, lastName, password));
      await this.router.navigate(["/admin-profile"]);
    } catch (error) {
      UtilService.displayError(error, this.router);
    }
  }
}
