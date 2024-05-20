import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth-components/login/login.component';
import { SignupComponent } from './auth-components/signup/signup.component';
import { noAuthGuard } from './auth-guards/noAuth-guard/no-auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate:[noAuthGuard]},
  { path: 'signup', component: SignupComponent, canActivate:[noAuthGuard]},
  { path: 'user', loadChildren: ()=> import("./user/user.module").then(m => m.UserModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
