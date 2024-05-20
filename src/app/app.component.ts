import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { StorageService } from './auth-services/storage-service/storage.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'spring-project-angular';

  isUserLoggedIn!: boolean;

  constructor(private router: Router){}

  ngOnInit(){
    this.updateUserLoggedInStatus();
    this.router.events.subscribe(event => {
      if(event instanceof NavigationEnd)
      {
        this.updateUserLoggedInStatus();
      }
    })
  }

  private updateUserLoggedInStatus(): void{
    this.isUserLoggedIn=StorageService.isUserLoggedIn();
  }

  logout()
  {
    StorageService.logout();
    this.router.navigateByUrl('/login');
  }
}
