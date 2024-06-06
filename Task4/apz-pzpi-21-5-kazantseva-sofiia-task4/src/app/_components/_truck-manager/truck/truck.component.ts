import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {TruckDTO} from "../../../_models/response/truck-dto";
import {TruckManagerService} from "../../../_services/truck-manager.service";

@Component({
  selector: 'app-truck',
  templateUrl: './truck.component.html',
  styleUrl: './truck.component.css'
})
export class TruckComponent {
  protected truck: TruckDTO | undefined;

  constructor(private truckManagerService: TruckManagerService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.getTruck(id);
  }

  protected async updateTruckByIot() {
    try {
      this.truck = await this.truckManagerService.updateTruckByIOT();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
    window.location.reload();
  }

  private async getTruck(id: number) {
    try {
      this.truck = await this.truckManagerService.getTruck(id);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
