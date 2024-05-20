import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { StorageService } from '../storage-service/storage.service';

const BASIC_URL=['http://localhost:8080/'];
export const AUTH_HEADER='authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private storage:StorageService) { }

  signup(signupRequest: any): Observable<any>{
    return this.http.post(BASIC_URL+"sign-up",signupRequest);
  }

  login(email: string, password: string): any{
    return this.http.post(BASIC_URL+"authenticate",
    {
      email,
      password
    },
    {observe: 'response'})
    .pipe(
      tap(__ => this.log("User Authentication")),
      map((res: HttpResponse<any>) => {
        this.storage.saveUser(res.body);
        const token = res.headers.get(AUTH_HEADER);
        if (token) {
          const tokenLength = token.length;
          const bearerToken = token.substring(7, tokenLength);
          this.storage.saveToken(bearerToken);
        }

        return res;
      })
    );
  }

  log(message: string):void{
    console.log("User Auth-Service "+message);
  }
}
