import {Component} from '@angular/core';
import {NotificationService} from "./notifications/notification.service";
import {BaseService} from "../services/base.service";

@Component({
    selector: 'app-chess',
    templateUrl: './chess.component.html',
    styleUrls: ['./chess.component.css']
})
export class ChessComponent {
    constructor(
        private notificationService: NotificationService,
        private baseService: BaseService) {

        this.baseService.checkStorageForToken();
    }
}
