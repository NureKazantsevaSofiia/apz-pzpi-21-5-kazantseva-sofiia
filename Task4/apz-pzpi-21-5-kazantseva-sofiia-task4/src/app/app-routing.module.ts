import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./_components/_general/login/login.component";
import {RegisterComponent} from "./_components/_general/register/register.component";
import {DatabaseComponent} from "./_components/_admin/database/database.component";
import {AdminUpdateProfileComponent} from "./_components/_admin/admin-update-profile/admin-update-profile.component";
import {AdminProfileComponent} from "./_components/_admin/admin-profile/admin-profile.component";
import {TruckManagersPageComponent} from "./_components/_admin/truck-managers-page/truck-managers-page.component";
import {ParkingManagersPageComponent} from "./_components/_admin/parking-managers-page/parking-managers-page.component";
import {HomeComponent} from "./_components/_general/home/home.component";
import {
  TruckManagerProfileComponent
} from "./_components/_truck-manager/truck-manager-profile/truck-manager-profile.component";
import {TruckUpdateComponent} from "./_components/_truck-manager/truck-update/truck-update.component";
import {TruckComponent} from "./_components/_truck-manager/truck/truck.component";
import {InvoiceUpdateComponent} from "./_components/_truck-manager/invoice-update/invoice-update.component";
import {
  ParkingManagerProfileComponent
} from "./_components/_parking-manager/parking-manager-profile/parking-manager-profile.component";
import {ParkingPlaceComponent} from "./_components/_parking-manager/parking-place/parking-place.component";
import {
  ParkingPlaceUpdateComponent
} from "./_components/_parking-manager/parking-place-update/parking-place-update.component";
import {UpdateProfileComponent} from "./_components/_general/update-profile/update-profile.component";
import {ParkingInvoiceComponent} from "./_components/_parking-manager/parking-invoice/parking-invoice.component";
import {TruckInvoicesComponent} from "./_components/_truck-manager/truck-invoices/truck-invoices.component";
import {ParkingInvoicesComponent} from "./_components/_parking-manager/parking-invoices/parking-invoices.component";
import {TruckInvoiceComponent} from "./_components/_truck-manager/truck-invoice/truck-invoice.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin-profile', component: AdminProfileComponent},
  {path: 'admin-profile/update', component: AdminUpdateProfileComponent},
  {path: 'database', component: DatabaseComponent},
  {path: 'truck-managers', component: TruckManagersPageComponent},
  {path: 'parking-managers', component: ParkingManagersPageComponent},
  {path: 'truck-manager', component: TruckManagerProfileComponent},
  {path: 'truck-manager/update', component: UpdateProfileComponent},
  {path: 'truck/:id', component: TruckComponent},
  {path: 'truck/update/:id', component: TruckUpdateComponent},
  {path: 'truck/create/new', component: TruckUpdateComponent},
  {path: 'truck-manager/invoices', component: TruckInvoicesComponent},
  {path: 'truck-manager/invoice/:id', component: TruckInvoiceComponent},
  {path: 'parking-manager/invoice/:id', component: ParkingInvoiceComponent},
  {path: 'invoice/create/new', component: InvoiceUpdateComponent},
  {path: 'parking-manager', component: ParkingManagerProfileComponent},
  {path: 'parking-manager/update', component: UpdateProfileComponent},
  {path: 'parking-place/:id', component: ParkingPlaceComponent},
  {path: 'parking-place/update/:id', component: ParkingPlaceUpdateComponent},
  {path: 'parking-place/create/new', component: ParkingPlaceUpdateComponent},
  {path: 'parking-manager/invoices', component: ParkingInvoicesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
