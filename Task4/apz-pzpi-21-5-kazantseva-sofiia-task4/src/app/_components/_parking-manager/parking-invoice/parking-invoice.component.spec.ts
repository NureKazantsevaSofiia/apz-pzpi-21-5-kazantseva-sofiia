import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParkingInvoiceComponent} from './parking-invoice.component';

describe('InvoiceComponent', () => {
  let component: ParkingInvoiceComponent;
  let fixture: ComponentFixture<ParkingInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingInvoiceComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ParkingInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
