import {Component} from '@angular/core';
import {NotificationService} from "./notifications/notification.service";
import {AppService} from "../services/app.service";

@Component({
    selector: 'app-chess',
    templateUrl: './chess.component.html',
    styleUrls: ['./chess.component.scss']
})
export class ChessComponent {
    constructor(
        private notificationService: NotificationService,
        private baseService: AppService) {

        this.baseService.checkStorageForToken();
    }
}
