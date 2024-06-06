import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingPlaceUpdateComponent } from './parking-place-update.component';

describe('ParkingPlaceUpdateComponent', () => {
  let component: ParkingPlaceUpdateComponent;
  let fixture: ComponentFixture<ParkingPlaceUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingPlaceUpdateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParkingPlaceUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
