import {Injectable} from '@angular/core';
import {RestService} from './rest.service';
import {HttpMethodTypeEnum} from '../models/application/http-method-type.enum';
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {ServerResponseType} from '../models/login/login-error-type.enum';
import {Headers, RequestOptions} from '@angular/http';
import {Router} from '@angular/router';
import {NotificationService} from '../application/notifications/notification.service';
import {LoginErrorModel} from '../models/login/login-error.model';
import {OauthService} from './oauth.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ValueType} from "../models/application/value-type.enum";

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

    executeHttpRequest(method: HttpMethodTypeEnum, path: string, valueType: ValueType, body?: any): Observable<any> {
        switch (method) {
            case HttpMethodTypeEnum.GET: {
                this.restService.get(path, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType))
                    // ,
                    // catchError(err => this.handleError(err, HttpMethodTypeEnum.GET, path))
                )
                    .subscribe(data => {
                        console.log("mam date");
                        return data;
                    }, error => {
                        console.log("mam error");
                        return error
                    });
            }
            case HttpMethodTypeEnum.POST: {
                return this.restService.post(path, body, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(err => this.handleError(err, HttpMethodTypeEnum.GET, path)));
            }
            case HttpMethodTypeEnum.PUT: {
                return this.restService.put(path, body, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(err => this.handleError(err, HttpMethodTypeEnum.GET, path)));
            }
            case HttpMethodTypeEnum.DELETE: {
                this.restService.delete(path, this.getOptions()).pipe(
                    map(response => this.mapByType(response, valueType)),
                    catchError(err => this.handleError(err, HttpMethodTypeEnum.GET, path)))
                    .subscribe(data => {
                        return data;
                    }, error => {
                        return error
                    });
            }
        }
    }

    private mapByType(response: any, valueType: ValueType): Observable<any> {
        if (valueType == ValueType.JSON) {
            return response.json();
        } else {
            return response.json();
        }
    }

    private handleServerErrorResponse(method: HttpMethodTypeEnum, response: ServerResponseType, path: string, body?: any): Observable<any> {
        switch (response) {
            case ServerResponseType.TOKEN_EXPIRED: {
                if (this.triesOfReloadToken < 2) {
                    this.triesOfReloadToken = this.triesOfReloadToken + 1;
                    this.notificationService.trace('Próba przeladowania tokenu.');
                    if (this.oauthService.refreshToken()) {
                        switch (method) {
                            case HttpMethodTypeEnum.GET: {
                                return this.restService.get(path, this.getOptions());
                            }
                            case HttpMethodTypeEnum.POST: {
                                return this.restService.post(path, body, this.getOptions());
                            }
                            case HttpMethodTypeEnum.PUT: {
                                return this.restService.put(path, body, this.getOptions());
                            }
                            case HttpMethodTypeEnum.DELETE: {
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

    public putFile(path: string, file: File): any {
        let formData: FormData = new FormData();
        formData.append('file', file);
        let options: any = {
            reportProgress: true,
            responseType: 'text',
            headers: {'Authorization': 'bearer ' + this.oauthService.getAccessToken()}
        };
        return this.restService.putFile(path, formData, options);
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
        // this.notificationService.info('Wylogowano');
    }

    private handleError(error: HttpErrorResponse, method: HttpMethodTypeEnum, path: string, body?: any) {
        if (error.status === 401) {
            this.logOut();
        }
        console.log("A WIEC ERRROR HANDELR SIE JEDNAK PRZYDAJE !!!!!!!!!!!!!!!!!!");
        console.log(error);

        return undefined;
    }

    private mapJSON(response: Observable<any>) {
        return response.pipe(map(response => response.json()));
    }

    private mapTEXT(response: Observable<any>) {
        return response.pipe(map(response => response.text()));
    }
}