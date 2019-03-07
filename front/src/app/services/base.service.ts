import {Injectable} from '@angular/core';
import {RestService} from './rest.service';
import {HttpMethodTypeEnum} from '../models/application/http-method-type.enum';
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Headers, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';
import {NotificationService} from '../application/notifications/notification.service';
import {OauthService} from './oauth.service';
import {ValueType} from "../models/application/value-type.enum";

@Injectable({
    providedIn: 'root',
})
export class BaseService {

    constructor(private restService: RestService,
                private router: Router,
                private oauthService: OauthService,
                private notificationService: NotificationService) {
    }

    executeHttpRequest(method: HttpMethodTypeEnum, path: string, valueType: ValueType, body?: any): Observable<any> {
        switch (method) {
            case HttpMethodTypeEnum.GET: {
                return this.restService.get(path, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(error => this.handleError(error, method, path, valueType, body)))
            }
            case HttpMethodTypeEnum.POST: {
                return this.restService.post(path, body, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(error => this.handleError(error, method, path, valueType, body)))
            }
            case HttpMethodTypeEnum.PUT: {
                return this.restService.put(path, body, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(error => this.handleError(error, method, path, valueType, body)))
            }
            case HttpMethodTypeEnum.DELETE: {
                this.restService.delete(path, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(error => this.handleError(error, method, path, valueType, body)))
            }
        }
    }

    private mapByType(response: any, valueType: ValueType): Observable<any> {
        if (valueType == ValueType.JSON) {
            return response.json();
        } else {
            return response.text();
        }
    }


    private handleError(error: any, method: HttpMethodTypeEnum, path: string, valueType: ValueType, body?: any): Observable<any> {
        if (error >= 500) {
            this.notificationService.info('Nierozpoznany bład aplikacji');
            this.logOut();
        } else if (error.status === 401) {
            if (error.json().error_description === 'Access token expired: ' + this.oauthService.getAccessToken()) {
                console.log(31);
                this.oauthService.refreshToken().subscribe(data => {
                    this.oauthService.token = data;
                    console.log(data);
                    this.notificationService.trace('Przeladowano token');
                }, error => {
                    this.notificationService.trace('Blad podczas przeladowania tokenu');
                    this.logOut();
                });
            }
            if (error.json().error_description === 'Invalid access token: ' + this.oauthService.getAccessToken()) {
                this.notificationService.info('Sesja wygasła zaloguj się ponownie!');
                this.logOut();
            }
        } else {
            return Observable.create(error);
        }
    }

    public putFile(path: string, file: File): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);

        return this.restService.putFile(path, formdata, {
            reportProgress: true,
            observe: 'events',
            headers: {'Authorization': 'bearer ' + this.oauthService.getAccessToken()}
        });
    }

    private getOptions(): RequestOptions {
        return new RequestOptions({headers: this.getHeaders()});
    }

    private getHeaders() {
        return new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'bearer ' + this.oauthService.getAccessToken()
        });
    }

    public logOut() {
        this.oauthService.clearToken();
        this.router.navigate(['/']);
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        // this.notificationService.info('Wylogowano');
    }
}