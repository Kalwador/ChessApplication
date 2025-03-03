import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {AppService} from '../../../services/app.service';
import {AccountModel} from '../../../models/profile/account.model';
import {NotificationService} from "../../notifications/notification.service";
import {RegisterModel} from "../../../models/register.model";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    path = '/profile';
    profile: AccountModel;

    constructor(private appService: AppService,
                private notificationService: NotificationService) {
    }

    getUserProfile(): Promise<AccountModel> {
        if (this.appService.isLoggedIn()) {
            this.notificationService.trace('ProfileService - User is loged in');
            if (this.appService.accountModel === null) {
                this.notificationService.trace('ProfileService - Account model not found - reloading');
                return this.appService.getUserProfile();
            }
            return new Promise(resolve => {
               resolve(this.appService.accountModel);
            });
        }
    }

    getProfileById(id: number): Observable<AccountModel> {
        return this.appService.get(this.path + '/' + id);
    }

    getNickById(id: number): Observable<string> {
        return this.appService.getText(this.path + '/nick/' + id);
    }

    updateInfo(profile: AccountModel): Observable<any> {
        return this.appService.put(this.path + "/info", profile);
    }

    updateDetails(details: RegisterModel): Observable<any> {
        return this.appService.put(this.path + "/details", details);
    }

    getAvatar(): Observable<any> {
        return this.appService.getText(this.path + '/avatar');
    }

    updateAvatar(file: File): Observable<any> {
        return this.appService.putFile(this.path + "/avatar", file);
    }

    checkExistByNick(nick: string) {
        return this.appService.getText(this.path + "/exist/" + nick);
    }

    isActivated(username: string) {
        return this.appService.getUnauthorized('/register/activate/' + username);
    }
}
