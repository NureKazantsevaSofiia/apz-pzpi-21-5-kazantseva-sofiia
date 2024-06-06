import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckInvoiceComponent } from './truck-invoice.component';

describe('TruckInvoiceComponent', () => {
  let component: TruckInvoiceComponent;
  let fixture: ComponentFixture<TruckInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TruckInvoiceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TruckInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
