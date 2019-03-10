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

    // thumbnail: string = null;

    constructor(public appService: AppService,
                private notificationService: NotificationService,
                private router: Router) {
        // this.reloadThumbnail();
    }

    test() {
        this.notificationService.trace('status zalogowania: ' + this.isUserLoggedIn());
        // this.router.navigate(['/game/play/pvp', 1]);
        this.notificationService.info("aaaaaaaaa");
    }

    public isUserLoggedIn(): boolean {
        return this.appService.isLoggedIn();
    }

    public logout() {
        this.appService.logOut();
    }
}
