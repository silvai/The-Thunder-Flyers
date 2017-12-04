import { Injectable } from '@angular/core';
import { User } from "./user";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable'
import { tap, catchError } from "rxjs/operators";
import { of } from "rxjs/observable/of";
import { APIMessage } from "./apimessage";

import jwt from "jsonwebtoken";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }
  
  login (user: User) : Observable<APIMessage> {
    return this.http.post<APIMessage>("http://localhost:3000/auth/login", user).pipe(
      tap(apimessage => {
        if (apimessage.success === true) {
          localStorage.setItem("token", apimessage.message);
          var decoded = jwt.decode(apimessage.message);
          localStorage.setItem("userid", decoded.sub);
        }
      }),
      catchError(this.handleError<APIMessage>("login"))
    )
  }

  register (user: User) : Observable<APIMessage> {
    return this.http.post<APIMessage>("http://localhost:3000/auth/register", user).pipe(
      tap(apimessage => console.dir(apimessage)),
      catchError(this.handleError<APIMessage>("register"))
    )
  }

  getUser (userid: number) : Observable<User> {
    return this.http.get<User>("http://localhost:3000/user/" + userid).pipe(
      tap(user => console.dir(user)),
      catchError(this.handleError<User>("getUser"))
    );
  }

  adminUnban() : Observable<APIMessage> {
    return this.http.post<APIMessage>("http://localhost:3000/admin/unban", "").pipe(
      tap(apiMessage => console.dir(apiMessage)),
      catchError(this.handleError<APIMessage>("adminUnban"))
    );
  }


  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
