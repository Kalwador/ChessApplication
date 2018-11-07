import {Component, OnInit} from '@angular/core';
import {BaseService} from '../../services/base.service';
import {NotificationService} from "../notifications/notification.service";

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent {

    constructor(private baseService: BaseService,
                private notificationService: NotificationService) {
    }

    test() {
        this.notificationService.trace('status zalogowania: ' + this.isUserLoggedIn());
    }

    reload() {
        this.baseService.reload();
    }

    public isUserLoggedIn(): boolean {
        return this.baseService.isLoggedIn();
    }

    public logout() {
        this.baseService.logOut();
    }
}
