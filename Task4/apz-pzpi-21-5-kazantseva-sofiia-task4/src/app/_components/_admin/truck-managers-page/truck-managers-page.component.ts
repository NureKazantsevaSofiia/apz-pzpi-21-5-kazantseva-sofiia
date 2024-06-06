import {Component} from '@angular/core';
import {TruckManagerDTO} from "../../../_models/response/truck-manager-dto";
import {UtilService} from "../../../_services/util.service";
import {AdminService} from "../../../_services/admin.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-truck-managers-page',
  templateUrl: './truck-managers-page.component.html',
  styleUrl: './truck-managers-page.component.css'
})
export class TruckManagersPageComponent {
  protected managers: TruckManagerDTO[] | undefined;
  protected readonly UtilService = UtilService;
  private managerId: bigint | undefined;

  constructor(private adminService: AdminService, private router: Router) {
  }

  ngOnInit() {
    this.getTruckManagers();
  }

  protected showPopup(managerId: bigint) {
    this.managerId = managerId;
    document.getElementById("popup")!.style.display = "flex";
  }

  protected hidePopup() {
    document.getElementById("popup")!.style.display = "none";
  }

  protected async deleteManager() {
    try {
      await this.adminService.deleteTruckManager(this.managerId!);
      location.reload();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  private async getTruckManagers() {
    try {
      this.managers = await this.adminService.getTruckManagers();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
