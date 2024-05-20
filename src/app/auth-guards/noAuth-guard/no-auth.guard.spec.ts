import { TestBed } from '@angular/core/testing';
import { CanActivateFn, Router } from '@angular/router';

import { noAuthGuard } from './no-auth.guard';

describe('noAuthGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => new noAuthGuard(TestBed.inject(Router)).canActivate(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
