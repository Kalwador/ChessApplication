import {Observable} from 'rxjs/internal/Observable';
import {Injectable} from '@angular/core';
import {Http, RequestOptions} from '@angular/http';
import {Option} from "@angular/cli/models/command";

@Injectable({
    providedIn: 'root',
})
export class RestService {
    public basicPath = 'http://localhost:8080';

    constructor(private http: Http) {
    }

    public get(path: string, options: RequestOptions): Observable<any> {
        return this.http.get(this.basicPath + path, options);
    }

    public post(path: string, body: any, options: RequestOptions): Observable<any> {
        return this.http.post(this.basicPath + path, body, options);
    }

    public put(path: string, body: any, options: RequestOptions): Observable<any> {
        return this.http.put(this.basicPath + path, body, options);
    }

    public delete(path: string, options: RequestOptions): Observable<any> {
        return this.http.delete(this.basicPath + path, options);
    }

    public putFile(path: string, body: any, options: any): Observable<any> {
        return this.http.put(this.basicPath + path, body, options);
    }
}

