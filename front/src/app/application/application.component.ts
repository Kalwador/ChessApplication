import {Component} from '@angular/core';
import {NotificationService} from "./notifications/notification.service";
import {AppService} from "../services/app.service";

@Component({
    selector: 'app-chess',
    templateUrl: './application.component.html',
    styleUrls: ['./application.component.scss']
})
export class ApplicationComponent {
    constructor(
        private notificationService: NotificationService,
        private baseService: AppService) {

        this.baseService.checkStorageForToken();
    }
}
