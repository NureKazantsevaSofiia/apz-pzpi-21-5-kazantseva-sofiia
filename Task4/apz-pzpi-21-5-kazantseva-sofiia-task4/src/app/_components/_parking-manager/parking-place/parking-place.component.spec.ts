import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingPlaceComponent } from './parking-place.component';

describe('ParkingPlaceComponent', () => {
  let component: ParkingPlaceComponent;
  let fixture: ComponentFixture<ParkingPlaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingPlaceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParkingPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
