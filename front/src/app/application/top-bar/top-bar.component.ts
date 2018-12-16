import {Component, OnInit} from '@angular/core';
import {AppService} from '../../services/app.service';
import {NotificationService} from '../notifications/notification.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent {

    constructor(public baseService: AppService,
                private notificationService: NotificationService,
                private router: Router) {
    }

    test() {
        this.notificationService.trace('status zalogowania: ' + this.isUserLoggedIn());
        this.router.navigate(['/game/play/pvp', 1]);
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
