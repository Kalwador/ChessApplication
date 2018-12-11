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

    getUserProfile(): AccountModel {
        return this.appService.accountModel;
    }

    getProfileById(id: number): Observable<AccountModel> {
        return this.appService.mapJSON(this.appService.get(this.path + '/' + id));
    }

    getNickById(id: number): Observable<string> {
        return this.appService.mapTEXT(this.appService.get(this.path + '/nick/' + id));
    }

    updateInfo(profile: AccountModel): Observable<any> {
        return this.appService.put(this.path + "/info", profile);
    }

    updateDetails(details: RegisterModel): Observable<any> {
        return this.appService.put(this.path + "/details", details);
    }

    updateAvatar(file: File): any {
        return this.appService.putFile(this.path + "/avatar", file);
    }

}
