import {Headers, Http, RequestOptions, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {LoginErrorModel} from '../models/login-error.model';
import {LoginErrorType} from '../models/login-error-type.enum';
import {AccountModel} from '../models/account.model';
import {RestService} from './rest.service';
import {OauthService} from './oauth.service';
import {Router} from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class BaseService {
    private accountModel: AccountModel;

    constructor(private restService: RestService,
                private oauthService: OauthService,
                public router: Router) {
    }

    public getUnAuthorized(path: string): Observable<any> {
        return this.restService.get(path, null);
    }

    public postUnAuthorized(path: string, body: any) {
        return this.restService.post(path, body, null);
    }

    public get(path: string): Observable<any> {
        return this.restService.get(path, this.getOptions()).pipe(map(response => {
                switch (this.checkResponseStatus(response)) {
                    case LoginErrorType.OK: {
                        return response;
                    }
                    case LoginErrorType.TOKEN_EXPIRED: {
                        if (this.oauthService.refreshToken()) {
                            return this.restService.get(path, this.getOptions());
                        }
                    }
                }
                this.logOut();
            }
        ));
    }

    public post(path: string, body: any): Observable<any> {
        return this.restService.post(path, body, this.getOptions()).pipe(map(response => {
                switch (this.checkResponseStatus(response)) {
                    case LoginErrorType.OK: {
                        return response;
                    }
                    case LoginErrorType.TOKEN_EXPIRED: {
                        if (this.oauthService.refreshToken()) {
                            return this.restService.post(path, body, this.getOptions());
                        }
                    }
                }
                this.logOut();
            }
        ));
    }

    public put(path: string, body: any): Observable<any> {
        return this.restService.put(path, body, this.getOptions()).pipe(map(response => {
                switch (this.checkResponseStatus(response)) {
                    case LoginErrorType.OK: {
                        return response;
                    }
                    case LoginErrorType.TOKEN_EXPIRED: {
                        if (this.oauthService.refreshToken()) {
                            return this.restService.put(path, body, this.getOptions());
                        }
                    }
                }
                this.logOut();
            }
        ));
    }

    public delete(path: string): Observable<any> {
        return this.restService.delete(path, this.getOptions()).pipe(map(response => {
                switch (this.checkResponseStatus(response)) {
                    case LoginErrorType.OK: {
                        return response;
                    }
                    case LoginErrorType.TOKEN_EXPIRED: {
                        if (this.oauthService.refreshToken()) {
                            return this.restService.delete(path, this.getOptions());
                        }
                    }
                }
                this.logOut();
            }
        ));
    }


    public postFile(path: string, file: File): Observable<any> {
        const formdata: FormData = new FormData();
        // formdata.append('file', file);
        // options: Options = {
        //     reportProgress: true,
        //     responseType: 'text',
        //     headers: this.getHeaders()
        // };
        // return this.restService.post(path, formdata, options);
        return null;
    }

    public logOut() {
        this.accountModel = null;
        this.oauthService.clearToken();
        this.router.navigate(['/']);
        console.log('Wylogowano');//TODO-NOTIF-SERVICE
        //TODO - need more stuff to do
    }

    private checkResponseStatus(response: Response): LoginErrorType {
        if (response.status === 200) {
            return LoginErrorType.OK;
        }
        if (response.status === 401) {
            return this.checkToken(response.json());
        } else {
            return LoginErrorType.ERROR;
        }
    }

    private checkToken(loginError: LoginErrorModel): LoginErrorType {
        if (loginError.error_description === 'Access token expired: ' + this.oauthService.getAccessToken()) {
            return LoginErrorType.TOKEN_EXPIRED;
        }
        if (loginError.error_description === 'Invalid access token: ' + this.oauthService.getAccessToken()) {
            return LoginErrorType.ERROR;
        }
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

    public mapJSON(response: Observable<any>) {
        return response.pipe(map(response => response.json()));
    }

    public mapTEXT(response: Observable<any>) {
        return response.pipe(map(response => response.text()));
    }


    isLogedIn() {
        return this.oauthService.isLogedIn();
    }
}
