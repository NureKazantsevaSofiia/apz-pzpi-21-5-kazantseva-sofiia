import {Component} from '@angular/core';
import {TruckManagerDTO} from "../../../_models/response/truck-manager-dto";
import {ParkingManagerDTO} from "../../../_models/response/parking-manager-dto";
import {LoginService} from "../../../_services/login.service";
import {TruckManagerService} from "../../../_services/truck-manager.service";
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {ManagerRequest} from "../../../_models/request/manager-request";

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.css'
})
export class UpdateProfileComponent {
  protected truckManager: TruckManagerDTO | undefined;
  protected parkingManager: ParkingManagerDTO | undefined;
  protected readonly LoginService = LoginService;

  constructor(private truckManagerService: TruckManagerService,
              private parkingManagerService: ParkingManagerService,
              private router: Router) {
  }

  ngOnInit() {
    if (LoginService.isTruckManager()) {
      this.getTruckManager();
    } else if (LoginService.isParkingManager()) {
      this.getParkingManager();
    } else {
      LoginService.logout(this.router)
    }
  }

  protected async updateTruckManagerProfile(avatar: string, firstName: string, lastName: string) {
    try {
      await this.truckManagerService.updateManager(new ManagerRequest(avatar, firstName, lastName));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/truck-manager']);
  }

  protected async updateParkingManagerProfile(avatar: string, firstName: string, lastName: string) {
    try {
      await this.parkingManagerService.updateManager(new ManagerRequest(avatar, firstName, lastName));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/parking-manager']);
  }

  private async getTruckManager() {
    try {
      this.truckManager = await this.truckManagerService.getManager();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  private async getParkingManager() {
    try {
      this.parkingManager = await this.parkingManagerService.getManager();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
