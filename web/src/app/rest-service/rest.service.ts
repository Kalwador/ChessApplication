import {Headers, RequestOptions, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {TokenModel} from '../models/token.model';
import {map} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {LoginErrorModel} from '../models/login-error.model';
import {LoginErrorType} from '../models/login-error-type.enum';
import {ApplicationCredentialsModel} from '../models/application-credentials.model';
import {RestService} from './rest.service';

@Injectable({
    providedIn: 'root',
})
export class RestService {
    private token: TokenModel;
    private basicPath = 'http://localhost:8080';

    //TODO - get app credentials
    private applicationCredentials: ApplicationCredentialsModel;

    private OAUTH_TOKEN = '/oauth/token';
    private OAUTH_USERNAME = '?username=';
    private OAUTH_PASSWORD = '&password=';
    private OAUTH_GRANT_TYPE = '&grant_type=password';
    private OAUTH_REFRESH_TOKEN = '?grant_type=refresh_token&refresh_token=';

    constructor(private restService: RestService) {
    }

    public get(path: string): Observable<any> {
        return this.restService.get(this.basicPath + path, this.getOptions())
            .pipe(map(response => {
                switch (this.checkResponseStatus(response)) {
                    case LoginErrorType.OK: {
                        return response.json();
                        break;
                    }
                    case LoginErrorType.TOKEN_EXPIRED: {
                        this.refreshToken();
                        //TODO zadbac o sprawdzenie czy refresh nie wygasł
                        // i wtedy zwrocic ponownie obiekt
                        return this.restService.get(this.basicPath + path).pipe(map(response => {

                        }

                        }
                    case LoginErrorType.ERROR: {
                            //TODO - wyloguj, wyrzuc na strone glowna
                            break;
                        }
                    }
                }
            ));
    }

    // public post(path: string, body: any): Observable<any> {
    //     return this.http.post(this.basicPath + path, body, this.getOptions()).pipe(map(response =>
    //         this.mapResponse(response)));
    // }

    public postFile(path: string, file: File): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);
        var options1 = this.getOptions();
        var options2 = {
            reportProgress: true,
            responseType: 'text'
        };

        return null;
        // return this.http.post(this.basicPath + path, formdata, );
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
        if (loginError.error_description === 'Access token expired: ' + this.token.acces_token) {
            return LoginErrorType.TOKEN_EXPIRED;
        }
        if (loginError.error_description === 'Invalid access token: ' + this.token.acces_token) {
            return LoginErrorType.ERROR;
        }
    }

    private refreshToken() {
        this.restService.post(this.basicPath + this.OAUTH_TOKEN + this.OAUTH_REFRESH_TOKEN + this.token.refresh_token, null, this.getOptionsForRefreshToken())
            .pipe(map(response => this.token = response.json()));
    }

    private getOptions(): RequestOptions {
        return new RequestOptions({headers: this.getHeaders()});
    }

    private getHeaders() {
        return new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'bearer ' + this.token.acces_token
        });
    }

    private getOptionsForRefreshToken(): RequestOptions {
        return new RequestOptions({
            headers: new Headers({
                'Authorization': 'bearer ' + this.encodeApplicationCredentials()
            })
        });
    }

    private encodeApplicationCredentials(): string {
        return btoa(this.applicationCredentials.client_id + ':' + this.applicationCredentials.secret);
    }
}
