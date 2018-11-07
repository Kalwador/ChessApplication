import {Injectable} from '@angular/core';
import {Headers, RequestOptions} from '@angular/http';
import {map} from 'rxjs/operators';
import {RestService} from './rest.service';
import {ApplicationCredentialsModel} from '../models/application-credentials.model';
import {TokenModel} from '../models/login/token.model';
import {Router} from '@angular/router';
import {NotificationService} from "../chess/notifications/notification.service";

@Injectable({
    providedIn: 'root',
})
export class OauthService {

    private OAUTH_TOKEN = '/oauth/token';
    private OAUTH_USERNAME = '?username=';
    private OAUTH_PASSWORD = '&password=';
    private OAUTH_GRANT_TYPE = '&grant_type=password';
    private OAUTH_REFRESH_TOKEN = '?grant_type=refresh_token&refresh_token=';

    //TODO - get app credentials
    private applicationCredentials: ApplicationCredentialsModel;
    private token: TokenModel = null;

    constructor(private restService: RestService,
                private router: Router,
                private notificationService: NotificationService) {
    }

    public login(username: string, password: string): boolean {
        this.restService.post(this.OAUTH_TOKEN
            + this.OAUTH_USERNAME + username
            + this.OAUTH_PASSWORD + password
            + this.OAUTH_GRANT_TYPE, null, this.getOptionsForToken())
            .pipe(map(response => response.json()))
            .subscribe(data => {
                this.token = data;
                this.putTokenToStorage(data);
                this.router.navigate(['/']);
                this.notificationService.trace('Poprawnie zalogowano');
            }, error => {
                switch (error.status) {
                    case (400): {
                        this.notificationService.warning('Błędny login i/lub hasło');
                        return false;
                    }
                    case (401): {
                        this.router.navigate(['/']);
                        this.notificationService.danger('Brak autoryzacji dla aplikacji');
                    }
                    default : {
                        this.router.navigate(['/']);
                        this.notificationService.danger('Nieznany błąd! ');
                    }
                }
            });
        return true;
    }

    public refreshToken() {
        this.restService.post(this.OAUTH_TOKEN + this.OAUTH_REFRESH_TOKEN + this.token.refresh_token, null, this.getOptionsForToken())
            .pipe(map(response => this.token = response.json()))
            .subscribe(data => {
                this.token = data;
                this.notificationService.trace('Przeladowano token');
            }, error => {
                //TODO NEED MORE WORK
            });
    }

    public clearToken() {
        this.applicationCredentials = null;
        this.token = null;
    }

    private getOptionsForToken(): RequestOptions {
        return new RequestOptions({
            headers: new Headers({
                'Authorization': 'Basic ' + this.encodeApplicationCredentials()
            })
        });
    }

    public getCredentials() {
        //TODO - just for now, need more thinking :)
        this.applicationCredentials = new ApplicationCredentialsModel();
        this.applicationCredentials.client_id = 'chessapp';
        this.applicationCredentials.secret = 'secret';
    }

    private encodeApplicationCredentials(): string {
        return btoa(this.applicationCredentials.client_id + ':' + this.applicationCredentials.secret);
    }

    public getAccessToken(): string {
        return this.token.access_token;
    }

    public isLoggedIn() {
        return this.token != null;
    }

    private putTokenToStorage(token: TokenModel){
        localStorage.setItem('access_token', token.access_token);
        localStorage.setItem('refresh_token', token.refresh_token);
    }

    public checkStorageForToken(): boolean {
        let access_token = localStorage.getItem('access_token');
        let refresh_token = localStorage.getItem('refresh_token');

        if (access_token != null && refresh_token != null) {
            this.token = new TokenModel();
            this.token.access_token = access_token;
            this.token.refresh_token = refresh_token;
            return true;
        }
        return false;
    }
}

