import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RatdataentryComponent } from './ratdataentry.component';

describe('RatdataentryComponent', () => {
  let component: RatdataentryComponent;
  let fixture: ComponentFixture<RatdataentryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatdataentryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatdataentryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
