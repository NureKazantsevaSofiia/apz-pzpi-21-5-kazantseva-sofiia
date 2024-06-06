import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckManagersPageComponent } from './truck-managers-page.component';

describe('TruckManagersPageComponent', () => {
  let component: TruckManagersPageComponent;
  let fixture: ComponentFixture<TruckManagersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TruckManagersPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TruckManagersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
