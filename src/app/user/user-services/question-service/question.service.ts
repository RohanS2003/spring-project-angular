import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage-service/storage.service';

const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) { }

  postQuestion(qDTO: any): Observable<any> {
    qDTO.userId = StorageService.getUserId();
    console.log(qDTO);
    return this.http.post<any>(BASIC_URL + "api/question", qDTO, {
      headers: this.createAuthorizationHeader()
    });
  }

  getAllQuestion(pageNumber: number): Observable<any>{
    return this.http.get<[]>(BASIC_URL+`api/questions/${pageNumber}`,
    {
      headers: this.createAuthorizationHeader()
    });
  }

  getQuestionById(questionId: number): Observable<any>{
    return this.http.get<[]>(BASIC_URL+`api/question/${questionId}/${StorageService.getUserId()}`,
    {
      headers: this.createAuthorizationHeader()
    });
  }

  getQuestionByUserId(pageNumber: number): Observable<any>{
    return this.http.get<[]>(BASIC_URL+`api/questions/${StorageService.getUserId()}/${pageNumber}`,
    {
      headers: this.createAuthorizationHeader()
    });
  }

  createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      "Authorization", "Bearer " + StorageService.getToken()
    );
  }

  addVoteToQuestion(voteQuestionDTO: any): Observable<any> {
    return this.http.post<any>(BASIC_URL + "api/vote", voteQuestionDTO, {
      headers: this.createAuthorizationHeader()
    });
  }
}
