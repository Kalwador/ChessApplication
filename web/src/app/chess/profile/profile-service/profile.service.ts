import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {AppService} from '../../../services/app.service';
import {AccountModel} from '../../../models/profile/account.model';
import {NotificationService} from "../../notifications/notification.service";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    path = '/profile';
    profile: AccountModel;

    constructor(private baseService: AppService,
                private notificationService: NotificationService) {
    }

    getUserProfile(): AccountModel {
        return this.baseService.accountModel;
    }

    getProfileById(id: number): Observable<AccountModel> {
        return this.baseService.mapJSON(this.baseService.get(this.path + '/' + id));
    }

    getNickById(id: number): Observable<string> {
        return this.baseService.mapTEXT(this.baseService.get(this.path + '/nick/' + id));
    }

    pushFileToStorage(file: File): Observable<any> {
        // const formdata: FormData = new FormData();
        //
        // formdata.append('file', file);
        //
        // return this.http.post(this.path, formdata, {
        //   reportProgress: true,
        //   responseType: 'text'
        // });

        // return this.postFile(this.path + 'profile/avatar', file);
        return null;
    }


}
