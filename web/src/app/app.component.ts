import {Component, OnInit} from '@angular/core';
import {BaseService} from "./services/base.service";
import {NotificationService} from "./chess/notifications/notification.service";
import {AppProfile} from "./models/app-info/app-profile.enum";

@Component({
    selector: 'app-root',
    template: `
        <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {

    constructor(private baseService: BaseService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.baseService.mapJSON(this.baseService.get('/info')).subscribe(data => {
            //app info i profil aplikacji
            this.baseService.appInfo = data;
            this.baseService.isDEVProfile = this.baseService.appInfo.profile === AppProfile.DEV;
            this.notificationService.isDevProfile = this.baseService.isDEVProfile;

            //avatar
            if (this.baseService.getAccountModel() !== null) {
                if (this.baseService.getAccountModel().avatar !== null) {
                    this.baseService.isAvatarAvailable = true;
                }
            }
        });
    }
}
