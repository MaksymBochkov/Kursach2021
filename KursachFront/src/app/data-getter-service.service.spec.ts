import { TestBed } from '@angular/core/testing';

import { DataGetterService } from './data-getter.service';

describe('DataGetterServiceService', () => {
  let service: DataGetterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataGetterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
