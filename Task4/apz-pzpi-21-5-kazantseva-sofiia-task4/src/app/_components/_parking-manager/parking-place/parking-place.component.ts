import {Component} from '@angular/core';
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {ParkingPlaceDTO} from "../../../_models/response/parking-place-dto";

@Component({
  selector: 'app-parking-place',
  templateUrl: './parking-place.component.html',
  styleUrl: './parking-place.component.css'
})
export class ParkingPlaceComponent {
  protected place: ParkingPlaceDTO | undefined;

  constructor(private parkingManagerService: ParkingManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.getParkingPlace(id);
  }

  protected async updatePlaceByIot() {
    try {
      this.place = await this.parkingManagerService.updatePlaceByIOT();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    window.location.reload();
  }

  private async getParkingPlace(id: number) {
    try {
      this.place = await this.parkingManagerService.getPlace(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
