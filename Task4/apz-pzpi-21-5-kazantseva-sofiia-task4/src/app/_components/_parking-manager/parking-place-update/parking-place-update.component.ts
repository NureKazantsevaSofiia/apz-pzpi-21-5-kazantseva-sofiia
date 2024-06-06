import {Component} from '@angular/core';
import {ParkingPlaceDTO} from "../../../_models/response/parking-place-dto";
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {PlaceRequest} from "../../../_models/request/place-request";
import {UpdatePlaceRequest} from "../../../_models/request/update-place-request";

@Component({
  selector: 'app-parking-place-update',
  templateUrl: './parking-place-update.component.html',
  styleUrl: './parking-place-update.component.css'
})
export class ParkingPlaceUpdateComponent {
  protected place: ParkingPlaceDTO | undefined;
  protected placeId: number = 0;

  constructor(private parkingManagerService: ParkingManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.placeId = +this.route.snapshot.paramMap.get('id')!;
    if (this.placeId != 0) {
      this.getParkingPlace(this.placeId);
    }
  }

  protected isNew() {
    return this.placeId == 0;
  }

  protected async createPlace(placeNumber: string, address: string, minWidth: string, maxWidth: string,
                              minLength: string, maxLength: string, minHeight: string, maxHeight: string,
                              minWeight: string, maxWeight: string, hourlyPay: string) {
    try {
      await this.parkingManagerService.createPlace(new PlaceRequest(placeNumber, address, minWidth,
        minHeight, minLength, minWeight, maxWidth, maxHeight, maxLength, maxWeight, hourlyPay));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/parking-manager']);
  }

  protected async updatePlace(minWidth: string, maxWidth: string,
                              minLength: string, maxLength: string, minHeight: string, maxHeight: string,
                              minWeight: string, maxWeight: string, hourlyPay: string) {
    try {
      await this.parkingManagerService.updatePlace(this.placeId!, new UpdatePlaceRequest(minWidth,
        minHeight, minLength, minWeight, maxWidth, maxHeight, maxLength, maxWeight, hourlyPay));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/parking-place', this.placeId]);
  }

  private async getParkingPlace(id: number) {
    try {
      this.place = await this.parkingManagerService.getPlace(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
