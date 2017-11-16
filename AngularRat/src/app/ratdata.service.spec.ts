import { TestBed, inject } from '@angular/core/testing';

import { RatdataService } from './ratdata.service';

describe('RatdataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RatdataService]
    });
  });

  it('should be created', inject([RatdataService], (service: RatdataService) => {
    expect(service).toBeTruthy();
  }));
});
