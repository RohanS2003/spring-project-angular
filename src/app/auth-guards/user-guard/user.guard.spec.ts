import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserGuard } from './user.guard';
import { StorageService } from './../../auth-services/storage-service/storage.service';

describe('UserGuard', () => {
  let guard: UserGuard;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [
        MatSnackBar,
        { provide: StorageService, useClass: StorageService } // You may need to provide a mock for StorageService if it's used in UserGuard
      ]
    });
    guard = TestBed.inject(UserGuard);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});


