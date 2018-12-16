import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AccountModel} from '../models/profile/account.model';
import {RestService} from './rest.service';
import {Router} from '@angular/router';
import {NotificationService} from "../application/notifications/notification.service";
import {AppInfoModel} from "../models/application/app-info.model";
import {HttpMethodTypeEnum} from "../models/application/http-method-type.enum";
import {BaseService} from "./base.service";
import {OauthService} from "./oauth.service";
import {ValueType} from "../models/application/value-type.enum";

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

    public getUnauthorized(path: string): Observable<any> {
        return this.restService.get(path, null);
    }

    public postUnAuthorized(path: string, body: any) {
        return this.restService.post(path, body, null);
    }

    public get(path: string): Observable<any> {
        this.notificationService.trace('get path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.GET, path, ValueType.JSON);
    }

    public post(path: string, body: any): Observable<any> {
        this.notificationService.info('post path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.POST, path, ValueType.JSON, body);
    }

    public put(path: string, body: any): Observable<any> {
        this.notificationService.trace('put path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.PUT, path, ValueType.JSON, body);
    }

    public delete(path: string): Observable<any> {
        this.notificationService.trace('delete path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.DELETE, path, ValueType.JSON);
    }

    public getText(path: string): Observable<any> {
        this.notificationService.trace('get path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.GET, path, ValueType.TEXT);
    }

    public postText(path: string, body: any): Observable<any> {
        this.notificationService.trace('post path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.POST, path, ValueType.TEXT, body);
    }

    public putText(path: string, body: any): Observable<any> {
        this.notificationService.trace('put path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.PUT, path, ValueType.TEXT, body);
    }

    public deleteText(path: string): Observable<any> {
        this.notificationService.trace('delete path: ' + path);
        return this.baseService.executeHttpRequest(HttpMethodTypeEnum.DELETE, path, ValueType.TEXT);
    }

    public putFile(path: string, file: File): any {
        return this.baseService.putFile(path, file);
    }

    public logOut() {
        this.accountModel = null;
        this.baseService.logOut();
    }

    public isLoggedIn() {
        return this.oauthService.isLoggedIn();
    }

    public reload() {
        this.get('http://localhost:8080/game/pve/reload');
    }

    public getUserProfile() {
        this.get("/profile").subscribe(data => {
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
        } else {
            this.logOut();
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
