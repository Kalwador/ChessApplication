import {Component, OnInit} from '@angular/core';
import {AppService} from "./services/app.service";
import {NotificationService} from "./chess/notifications/notification.service";
import {AppProfileEnum} from "./models/app-info/app-profile.enum";

@Component({
    selector: 'app-root',
    template: `
        <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {

    constructor(private baseService: AppService,
                private notificationService: NotificationService) {

        this.baseService.mapJSON(this.baseService.getUnAuthorized('/info')).subscribe(data => {
            //app info i profil aplikacji
            this.baseService.appInfo = data;
            let isDev = this.baseService.appInfo.profile === AppProfileEnum.DEV;
            this.baseService.appInfo.profile = AppProfileEnum.DEV;

            this.baseService.isDEVProfile = isDev;
            this.notificationService.isDevProfile = isDev;

        });
    }

    ngOnInit() {

    }
}
