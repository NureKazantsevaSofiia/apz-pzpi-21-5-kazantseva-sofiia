import {Component, ViewChild} from '@angular/core';
import {TruckManagerService} from "../../../_services/truck-manager.service";
import {ParkingManagerService} from "../../../_services/parking-manager.service";
import {Router} from "@angular/router";
import {RegisterRequest} from "../../../_models/request/register-request";
import {UtilService} from "../../../_services/util.service";
import {FormControl} from "@angular/forms";
import {map, Observable, of, startWith} from "rxjs";
import {CompanyDTO} from "../../../_models/response/company-dto";
import {MatAutocompleteTrigger} from "@angular/material/autocomplete";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  dataControl = new FormControl();
  dataOptions: CompanyDTO[] = [];
  filteredOptions: Observable<CompanyDTO[]> = of([]);
  selectedOption: any;
  @ViewChild(MatAutocompleteTrigger) autocompleteTrigger!: MatAutocompleteTrigger;
  private role = 0;

  constructor(private truckManagerService: TruckManagerService,
              private parkingManagerService: ParkingManagerService,
              private router: Router) {
  }

  registerTruckManager(): void {
    this.role = 0;
    let registerRole = document.getElementById("registerRole");
    let registerForm = document.getElementById("registerForm");
    if (registerRole != null && registerForm != null) {
      registerRole.style.display = "none";
      registerForm.style.display = "block";
    }

    this.truckManagerService.getCompanyList().subscribe(data => {
      this.dataOptions = data;
    });

    this.dataControl.valueChanges.pipe(startWith(null)).subscribe(value => {
      if (value === null) {
        this.filteredOptions = of(this.dataOptions);
      }
    });

    this.filteredOptions = this.dataControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  registerParkingManager(): void {
    this.role = 1;
    let registerRole = document.getElementById("registerRole");
    let registerForm = document.getElementById("registerForm");
    if (registerRole != null && registerForm != null) {
      registerRole.style.display = "none";
      registerForm.style.display = "block";
    }

    this.parkingManagerService.getCompanyList().subscribe(data => {
      this.dataOptions = data;
    });

    this.dataControl.valueChanges.pipe(startWith(null)).subscribe(value => {
      if (value === null) {
        this.filteredOptions = of(this.dataOptions);
      }
    });

    this.filteredOptions = this.dataControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  register(firstName: string, lastName: string, email: string, password: string): void {
    let data = new RegisterRequest(firstName, lastName, email, password, this.selectedOption.id);
    this.parkingManagerService.register(data).subscribe(response => {
      },
      (error) => {
        UtilService.displayAuthError(error)
      });
    this.router.navigate(['/login']);
  }

  onOptionSelected(selected: CompanyDTO): void {
    this.selectedOption = selected;
  }

  displayFn(option: any): string {
    return option ? option.name : '';
  }

  openAutocomplete() {
    this.autocompleteTrigger.openPanel();
  }

  private _filter(value: string): any[] {
    const filterValue = value.toString().toLowerCase();
    return this.dataOptions.filter(option => option.name.toLowerCase().includes(filterValue));
  }
}
