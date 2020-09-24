import { TestBed } from '@angular/core/testing';


import { PeliService } from './peli.service';

describe('PeliService', () => {
  let service: PeliService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeliService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
