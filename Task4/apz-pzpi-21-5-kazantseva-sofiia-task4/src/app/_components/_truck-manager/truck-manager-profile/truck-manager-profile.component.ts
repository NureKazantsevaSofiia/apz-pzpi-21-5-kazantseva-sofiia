import {Component} from '@angular/core';
import {PaginatedResponse} from "../../../_models/response/paginated-response";
import {Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {LoginService} from "../../../_services/login.service";
import {TruckManagerDTO} from "../../../_models/response/truck-manager-dto";
import {ShortTruckDTO} from "../../../_models/response/short-truck-dto";
import {TruckManagerService} from "../../../_services/truck-manager.service";

@Component({
  selector: 'app-truck-manager-profile',
  templateUrl: './truck-manager-profile.component.html',
  styleUrl: './truck-manager-profile.component.css'
})
export class TruckManagerProfileComponent {
  protected manager: TruckManagerDTO | undefined;
  protected trucks: PaginatedResponse<ShortTruckDTO> | undefined;
  protected currentPage: number = 0;
  protected totalPages: number = 0;

  constructor(private managerService: TruckManagerService, private router: Router) {
  }

  ngOnInit() {
    this.getManagerProfile().then(r => {
      this.getTrucks(0)
    });
  }

  protected async updateTruck(truckId: bigint) {
    await this.router.navigate(['/truck/update', truckId]);
  }

  protected async openTruck(truckId: bigint) {
    await this.router.navigate(['/truck', truckId]);
  }

  protected async deleteTruck(truckId: bigint) {
    try {
      await this.managerService.deleteTruck(truckId);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async changePage(number: number) {
    if (number > 0 && number <= this.totalPages) {
      await this.getTrucks(number - 1);
    }
  }

  protected async getTrucks(page: number) {
    try {
      this.trucks = await this.managerService.getTrucksByCompany(page);
      this.currentPage = this.trucks?.number! + 1;
      this.totalPages = this.trucks?.totalPages!;
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected showPopup() {
    document.getElementById("popup")!.style.display = "flex";
  }

  protected hidePopup() {
    document.getElementById("popup")!.style.display = "none";
  }

  protected async deleteProfile() {
    document.getElementById("popup")!.style.display = "none";
    try {
      await this.managerService.deleteManager()
      await LoginService.logout(this.router)
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async createTruck() {
    await this.router.navigate(['/truck/create/new']);
  }

  private async getManagerProfile() {
    try {
      this.manager = await this.managerService.getManager();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
