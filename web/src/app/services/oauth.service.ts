import {Injectable} from '@angular/core';
import {Headers, RequestOptions} from '@angular/http';
import {map} from 'rxjs/operators';
import {RestService} from './rest.service';
import {ApplicationCredentialsModel} from '../models/application-credentials.model';
import {TokenModel} from '../models/login/token.model';
import {Router} from '@angular/router';
import {ProfileService} from '../chess/profile/profile-service/profile.service';

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
    private token: TokenModel;

    constructor(private restService: RestService,
                private router: Router) {
    }

    public login(username: string, password: string): boolean {
        this.restService.post(this.OAUTH_TOKEN
            + this.OAUTH_USERNAME + username
            + this.OAUTH_PASSWORD + password
            + this.OAUTH_GRANT_TYPE, null, this.getOptionsForToken())
            .pipe(map(response => response.json()))
            .subscribe(data => {
                this.token = data;
                this.router.navigate(['/']);
                // .then(() => location.reload());
                console.log('Poprawnie zalogowano');//TODO-NOTIF-SERVICE
            }, error => {
                switch (error.status) {
                    case (400): {
                        console.log('Błędny login i/lub hasło');//TODO-NOTIF-SERVICE
                        return false;
                    }
                    case (401): {
                        this.router.navigate(['/']);
                        console.log('Brak autoryzacji dla aplikacji');//TODO-NOTIF-SERVICE
                    }
                    default : {
                        this.router.navigate(['/']);
                        console.log('Nieznany błąd! ');//TODO-NOTIF-SERVICE
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
                console.log('Przeladowano token');
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

    isLogedIn() {
        return this.token != null;
    }
}

