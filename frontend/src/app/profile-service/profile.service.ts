import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  url = 'http://localhost:8080/profile/avatar';
  constructor(private http: HttpClient) {}

  pushFileToStorage(file: File): Observable<any> {
    let formdata: FormData = new FormData();

    formdata.append('file', file);

    // const req = new HttpRequest('POST', , formdata, {
    //   reportProgress: true,
    //   responseType: 'text'
    // });
    // return this.http.request(req);

    console.log("new ");

    return this.http.post(this.url, formdata, {
      reportProgress: true,
      responseType: 'text'
    });
  }
}
