import { StorageService } from './../../auth-services/storage-service/storage.service';
import { CanActivate, ActivatedRouteSnapshot,Router, RouterStateSnapshot} from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class noAuthGuard implements CanActivate {

  constructor(private router: Router){}
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(StorageService.hasToken())
    {
      this.router.navigateByUrl("/user/dashboard");
      return false;
    }
    return true;
  }


};
