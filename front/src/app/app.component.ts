import {Component, OnInit} from '@angular/core';
import {AppService} from "./services/app.service";
import {NotificationService} from "./application/notifications/notification.service";
import {map} from "rxjs/operators";
import {AppProfileEnum} from "./models/application/app-profile.enum";

@Component({
    selector: 'app-root',
    template: `
        <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {

    constructor(private baseService: AppService,
                private notificationService: NotificationService) {

        this.baseService.getUnauthorized('/info').pipe(map(response => response.text()))
            .subscribe(data => {
                this.baseService.appInfo = data;
                let isDev = this.baseService.appInfo.profile === AppProfileEnum.DEV;
                this.baseService.isDEVProfile = isDev;
                this.notificationService.isDevProfile = isDev;
            });
    }

    ngOnInit() {

    }
}
