import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingManagerProfileComponent } from './parking-manager-profile.component';

describe('ParkingManagerProfileComponent', () => {
  let component: ParkingManagerProfileComponent;
  let fixture: ComponentFixture<ParkingManagerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingManagerProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParkingManagerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
