import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {AccountModel} from '../models/profile/account.model';
import {RestService} from './rest.service';
import {Router} from '@angular/router';
import {NotificationService} from "../chess/notifications/notification.service";
import {AppInfoModel} from "../models/app-info/app-info.model";
import {HttpMethodType} from "../models/http-method-type.enum";
import {BaseService} from "./base.service";
import {OauthService} from "./oauth.service";

@Injectable({
    providedIn: 'root',
})
export class AppService {
    public accountModel: AccountModel = null;
    public appInfo: AppInfoModel;

    public isDEVProfile: boolean = false;
    public isAvatarAvailable: boolean = false;
    public isUserLoggedIn: boolean = false;


    constructor(private restService: RestService,
                private baseService: BaseService,
                private oauthService: OauthService,
                private router: Router,
                private notificationService: NotificationService) {
    }

    public getUnAuthorized(path: string): Observable<any> {
        return this.restService.get(path, null);
    }

    public postUnAuthorized(path: string, body: any) {
        return this.restService.post(path, body, null);
    }

    public get(path: string): Observable<any> {
        this.notificationService.trace('get path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodType.GET, path);
    }

    public post(path: string, body: any): Observable<any> {
        this.notificationService.trace('post path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodType.POST, path, body);
    }

    public put(path: string, body: any): Observable<any> {
        this.notificationService.trace('put path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodType.PUT, path, body);
    }

    public delete(path: string): Observable<any> {
        this.notificationService.trace('delete path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodType.DELETE, path);
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
        this.baseService.logOut();
    }


    public mapJSON(response: Observable<any>) {
        return response.pipe(map(response => response.json()));
    }

    public mapTEXT(response: Observable<any>) {
        return response.pipe(map(response => response.text()));
    }

    public isLoggedIn() {
        return this.oauthService.isLoggedIn();
    }

    public reload() {
        this.get('http://localhost:8080/game/pve/reload');
    }

    public getUserProfile() {
        this.mapJSON(this.get("/profile")).subscribe(data => {
            this.accountModel = data;
            this.notificationService.trace("Pobrano account model, nick: " + this.accountModel.username);

            if (this.accountModel.avatar !== null) {
                this.isAvatarAvailable = true;
            }
        });
    }

    public checkStorageForToken() {
        if (this.oauthService.checkStorageForToken()) {
            this.getUserProfile();
            this.notificationService.trace("Znaleziono token w storage")
        }
    }

    public getPaging(page: number, size: number) {
        return '?page=' + page + '&size=' + size;
    }

    public getPagingAndSorting(page: number, size: number, sort: string, sortType: string) {
        return '?page=' + page + '&size=' + size + '&sort=' + sort + ',' + sortType;
    }

    public getBasePath() {
        return this.restService.basicPath;
    }
}
