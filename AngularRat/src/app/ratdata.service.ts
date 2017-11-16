import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable'
import { tap, catchError } from "rxjs/operators";
import { of } from "rxjs/observable/of";
import { APIMessage } from "./apimessage";
import { RatData } from "./ratdata";

@Injectable()
export class RatdataService {
  constructor(private http: HttpClient) { }

  getRatDataByDatePage(lastId: number, millis: number): Observable<RatData[]> {
    return this.http.get<RatData[]>("/data/" + lastId + "/" + millis).pipe(
      tap(ratDataArray => console.dir(ratDataArray)),
      catchError(this.handleError("getRatDataByDatePage", []))
    );
  }

  getRatDataByDateRange(minDate: Date, maxDate: Date): Observable<RatData[]> {
    return this.http.get<RatData[]>("/data/search/" + minDate.getMilliseconds() + "/" + maxDate.getMilliseconds()).pipe(
      tap(ratDataArray => console.dir(ratDataArray)),
      catchError(this.handleError("getRatDataByDateRange", []))
    );
  }

  addRatData(data: RatData): Observable<APIMessage> {
    return this.http.post<APIMessage>("/data/add", data).pipe(
      tap(apimessage => console.dir(apimessage)),
      catchError(this.handleError<APIMessage>("addRatData"))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
