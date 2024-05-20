import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { Observable } from 'rxjs';

const BASIC_URL=["http://localhost:8080/"]

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) { }

  postAnswer(answerDTO: any): Observable<any> {
    return this.http.post<[]>(BASIC_URL + "api/answer", answerDTO,
    {
      headers: this.createAuthorizationHeader()
    });
  }

  postAnswerImage(file: any, answerId: number): Observable<any>
  {
    return this.http.post(BASIC_URL+`api/image/${answerId}`, file,
    {
      headers: this.createAuthorizationHeader(),
      responseType: 'text' as 'json' // Type assertion
    });
  }

  approveAnswer(answerId: number): Observable<any>
  {
    return this.http.get(BASIC_URL+`api/answer/${answerId}`,
    {
      headers: this.createAuthorizationHeader(),
    });
  }

  createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      "Authorization", "Bearer " + StorageService.getToken()
    );
  }
}
