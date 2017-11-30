import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RatdatadisplayComponent } from './ratdatadisplay.component';

describe('RatdatadisplayComponent', () => {
  let component: RatdatadisplayComponent;
  let fixture: ComponentFixture<RatdatadisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatdatadisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatdatadisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
