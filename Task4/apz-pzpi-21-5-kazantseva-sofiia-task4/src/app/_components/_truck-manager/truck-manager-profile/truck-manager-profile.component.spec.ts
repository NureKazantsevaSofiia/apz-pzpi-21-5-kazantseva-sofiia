import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckManagerProfileComponent } from './truck-manager-profile.component';

describe('TruckManagerProfileComponent', () => {
  let component: TruckManagerProfileComponent;
  let fixture: ComponentFixture<TruckManagerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TruckManagerProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TruckManagerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
