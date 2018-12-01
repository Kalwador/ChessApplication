import {Component, OnInit} from '@angular/core';
import {AppService} from "./services/app.service";
import {NotificationService} from "./chess/notifications/notification.service";
import {AppProfile} from "./models/app-info/app-profile.enum";

@Component({
    selector: 'app-root',
    template: `
        <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {

    constructor(private baseService: AppService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.baseService.mapJSON(this.baseService.getUnAuthorized('/info')).subscribe(data => {
            //app info i profil aplikacji
            this.baseService.appInfo = data;
            // let isDev = this.baseService.appInfo.profile === AppProfile.DEV;

            let isDev = true;
            this.baseService.appInfo.profile = AppProfile.DEV;

            this.baseService.isDEVProfile = isDev;
            this.notificationService.isDevProfile = isDev;

        });
    }
}
