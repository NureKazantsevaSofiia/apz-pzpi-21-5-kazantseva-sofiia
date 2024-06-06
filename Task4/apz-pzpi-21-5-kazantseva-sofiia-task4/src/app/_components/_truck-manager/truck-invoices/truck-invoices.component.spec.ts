import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckInvoicesComponent } from './truck-invoices.component';

describe('TruckInvoicesComponent', () => {
  let component: TruckInvoicesComponent;
  let fixture: ComponentFixture<TruckInvoicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TruckInvoicesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TruckInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
