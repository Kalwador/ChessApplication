import {Injectable} from '@angular/core';
import {RestService} from './rest.service';
import {HttpMethodType} from '../models/http-method-type.enum';
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {ServerResponseType} from '../models/login/login-error-type.enum';
import {Headers, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';
import {NotificationService} from '../chess/notifications/notification.service';
import {LoginErrorModel} from '../models/login/login-error.model';
import {OauthService} from './oauth.service';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class BaseService {

    private triesOfReloadToken: number = 0;

    constructor(private restService: RestService,
                private router: Router,
                private oauthService: OauthService,
                private notificationService: NotificationService) {
    }

    executeHttpRequest(method: HttpMethodType, path: string, body?: any): Observable<any> {
        switch (method) {
            case HttpMethodType.GET: {
                return this.restService.get(path, this.getOptions()).pipe(
                    map(response => response),
                    catchError(err => this.handleError(err, HttpMethodType.GET, path)));
            }
            case HttpMethodType.POST: {
                return this.restService.post(path, body, this.getOptions()).pipe(
                    map(response => response),
                    catchError(err => this.handleError(err, HttpMethodType.PUT, path, body)));
            }
            case HttpMethodType.PUT: {
                return this.restService.put(path, body, this.getOptions()).pipe(
                    map(response => response),
                    catchError(err => this.handleError(err, HttpMethodType.POST, path, body)));
            }
            case HttpMethodType.DELETE: {
                return this.restService.delete(path, this.getOptions()).pipe(
                    map(response => response),
                    catchError(err => this.handleError(err, HttpMethodType.DELETE, path)));
            }
        }
    }

    private handleServerErrorResponse(method: HttpMethodType, response: ServerResponseType, path: string, body?: any): Observable<any> {
        switch (response) {
            case ServerResponseType.TOKEN_EXPIRED: {
                if (this.triesOfReloadToken < 2) {
                    this.triesOfReloadToken = this.triesOfReloadToken + 1;
                    this.notificationService.trace('Próba przeladowania tokenu.');
                    if (this.oauthService.refreshToken()) {
                        switch (method) {
                            case HttpMethodType.GET: {
                                return this.restService.get(path, this.getOptions());
                            }
                            case HttpMethodType.POST: {
                                return this.restService.post(path, body, this.getOptions());
                            }
                            case HttpMethodType.PUT: {
                                return this.restService.put(path, body, this.getOptions());
                            }
                            case HttpMethodType.DELETE: {
                                return this.restService.delete(path, this.getOptions());
                            }
                        }
                    }
                }
            }
            case ServerResponseType.ERROR:
            default: {
                this.notificationService.warning('Sesja wygasła, prosimy zalogować się ponownie');
                this.logOut();
            }
        }
    }

    private getOptions(): RequestOptions {
        return new RequestOptions({headers: this.getHeaders()});
    }

    private getHeaders() {
        return new Headers({
            'Content-Type': 'application/json'
            , 'Authorization': 'bearer ' + this.oauthService.getAccessToken()
        });
    }

    private checkResponseStatus(response: any): ServerResponseType {
        switch (response.status) {
            case 200 :
            case 201 :
            case 204 : {
                return ServerResponseType.OK;
            }
            case 401 : {
                return this.checkToken(response.json());
            }
            default : {
                this.notificationService.trace('Error to: ');
                this.notificationService.trace(response);
                return ServerResponseType.ERROR;
            }
        }
    }

    private checkToken(loginError: LoginErrorModel): ServerResponseType {
        if (loginError.error_description === 'Access token expired: ' + this.oauthService.getAccessToken()) {
            return ServerResponseType.TOKEN_EXPIRED;
        }
        if (loginError.error_description === 'Invalid access token: ' + this.oauthService.getAccessToken()) {
            return ServerResponseType.ERROR;
        }
    }

    public logOut() {
        this.oauthService.clearToken();
        this.router.navigate(['/']);
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        this.notificationService.info('Wylogowano');
    }

    private handleError(error: HttpErrorResponse, method: HttpMethodType, path: string, body?: any) {
        // console.log(error);
        // let temp = this.checkResponseStatus(error);
        if (error.status === 401) {
            this.logOut();
        }
        console.log("handle error ");
        console.log(error);

        return undefined;
    }
}