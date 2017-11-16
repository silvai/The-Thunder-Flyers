import { Injectable } from '@angular/core';
import { User } from "./user";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable'
import { tap, catchError } from "rxjs/operators";
import { of } from "rxjs/observable/of";
import { APIMessage } from "./apimessage";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }
  
  login (user: User) : Observable<APIMessage> {
    return this.http.post<APIMessage>("/auth/login", user).pipe(
      tap(apimessage => console.dir(apimessage)),
      catchError(this.handleError<APIMessage>("login"))
    )
  }

  register (user: User) : Observable<APIMessage> {
    return this.http.post<APIMessage>("/auth/register", user).pipe(
      tap(apimessage => console.dir(apimessage)),
      catchError(this.handleError<APIMessage>("register"))
    )
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
