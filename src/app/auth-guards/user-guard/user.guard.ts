import { StorageService } from './../../auth-services/storage-service/storage.service';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot, GuardResult } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})

export class UserGuard implements CanActivate {

  constructor(private router: Router, private snackBar: MatSnackBar) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):boolean {
    if (!StorageService.hasToken()) {
      StorageService.signOut();
      this.router.navigateByUrl("/login");
      this.snackBar.open(
        "You are not logged in, Login first",
        "Close",
        { duration: 5000 }
      );
      return false;
    }
    return true;
  }
}
