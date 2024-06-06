import {Component} from '@angular/core';
import {AdminService} from "../../../_services/admin.service";
import {Router} from "@angular/router";
import {ParkingManagerDTO} from "../../../_models/response/parking-manager-dto";
import {UtilService} from '../../../_services/util.service';

@Component({
  selector: 'app-parking-managers-page',
  templateUrl: './parking-managers-page.component.html',
  styleUrl: './parking-managers-page.component.css'
})
export class ParkingManagersPageComponent {
  protected managers: ParkingManagerDTO[] | undefined;
  protected readonly UtilService = UtilService;
  private managerId: bigint | undefined;

  constructor(private adminService: AdminService, private router: Router) {
  }

  ngOnInit() {
    this.getParkingManagers();
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
      await this.adminService.deleteParkingManager(this.managerId!);
      location.reload();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  private async getParkingManagers() {
    try {
      this.managers = await this.adminService.getParkingManagers();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
