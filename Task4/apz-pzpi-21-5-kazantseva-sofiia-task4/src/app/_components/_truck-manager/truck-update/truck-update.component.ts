import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {TruckDTO} from "../../../_models/response/truck-dto";
import {TruckManagerService} from "../../../_services/truck-manager.service";
import {TruckRequest} from "../../../_models/request/truck-request";
import {UpdateTruckRequest} from "../../../_models/request/update-truck-request";

@Component({
  selector: 'app-truck-update',
  templateUrl: './truck-update.component.html',
  styleUrl: './truck-update.component.css'
})
export class TruckUpdateComponent {
  protected truck: TruckDTO | undefined;
  protected truckId: number = 0;

  constructor(private truckManagerService: TruckManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.truckId = +this.route.snapshot.paramMap.get('id')!;
    if (this.truckId != 0) {
      this.getTruck(this.truckId);
    }
  }

  protected isNew() {
    return this.truckId == 0;
  }

  protected async createTruck(number: string, width: string, length: string, height: string, weight: string) {
    try {
      await this.truckManagerService.createTruck(new TruckRequest(number, width, height, length, weight));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/truck-manager']);
  }

  protected async updateTruck(width: string, length: string,
                              height: string, weight: string) {
    try {
      await this.truckManagerService.updateTruck(this.truckId!, new UpdateTruckRequest(width, height,
        length, weight));
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    await this.router.navigate(['/truck', this.truckId]);
  }

  private async getTruck(id: number) {
    try {
      this.truck = await this.truckManagerService.getTruck(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
