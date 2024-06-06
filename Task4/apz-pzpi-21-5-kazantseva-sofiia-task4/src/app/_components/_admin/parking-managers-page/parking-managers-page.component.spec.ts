import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingManagersPageComponent } from './parking-managers-page.component';

describe('ParkingManagersPageComponent', () => {
  let component: ParkingManagersPageComponent;
  let fixture: ComponentFixture<ParkingManagersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParkingManagersPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParkingManagersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
