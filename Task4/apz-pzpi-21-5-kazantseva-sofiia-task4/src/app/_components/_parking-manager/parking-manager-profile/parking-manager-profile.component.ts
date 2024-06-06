import {Component} from '@angular/core';
import {ParkingManagerDTO} from "../../../_models/response/parking-manager-dto";
import {Router} from "@angular/router";
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {LoginService} from "../../../_services/login.service";
import {UtilService} from "../../../_services/util.service";
import {PaginatedResponse} from "../../../_models/response/paginated-response";
import {ShortParkingPlaceDTO} from "../../../_models/response/short-parking-place-dto";

@Component({
  selector: 'app-parking-manager-profile',
  templateUrl: './parking-manager-profile.component.html',
  styleUrl: './parking-manager-profile.component.css'
})
export class ParkingManagerProfileComponent {
  protected manager: ParkingManagerDTO | undefined;
  protected places: PaginatedResponse<ShortParkingPlaceDTO> | undefined;
  protected currentPage: number = 0;
  protected totalPages: number = 0;

  constructor(private managerService: ParkingManagerService, private router: Router) {
  }

  ngOnInit() {
    this.getManagerProfile().then(r => {
      this.getParkingPlaces(0)
    });
  }

  protected async updatePlace(placeId: bigint) {
    await this.router.navigate(['/parking-place/update', placeId]);
  }

  protected async openPlace(placeId: bigint) {
    await this.router.navigate(['/parking-place', placeId]);
  }

  protected async deletePlace(placeId: bigint) {
    try {
      await this.managerService.deletePlace(placeId);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async changePage(number: number) {
    if (number > 0 && number <= this.totalPages) {
      await this.getParkingPlaces(number - 1);
    }
  }

  protected async getParkingPlaces(page: number) {
    try {
      this.places = await this.managerService.getPlacesByCompany(page);
      this.currentPage = this.places?.number! + 1;
      this.totalPages = this.places?.totalPages!;
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

  protected async createPlace() {
    await this.router.navigate(['/parking-place/create/new']);
  }

  private async getManagerProfile() {
    try {
      this.manager = await this.managerService.getManager();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
