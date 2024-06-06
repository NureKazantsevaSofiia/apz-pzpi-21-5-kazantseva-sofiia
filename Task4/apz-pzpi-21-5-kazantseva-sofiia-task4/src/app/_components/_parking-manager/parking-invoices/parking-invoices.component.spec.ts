import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingInvoicesComponent } from './parking-invoices.component';

describe('ParkingInvoicesComponent', () => {
  let component: ParkingInvoicesComponent;
  let fixture: ComponentFixture<ParkingInvoicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingInvoicesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParkingInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
