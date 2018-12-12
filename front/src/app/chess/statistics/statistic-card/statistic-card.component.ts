import {Component, OnInit} from '@angular/core';
import {AccountModel} from "../../../models/profile/account.model";
import {AppService} from "../../../services/app.service";
import {ProfileService} from "../../profile/profile-service/profile.service";
import {NotificationService} from "../../notifications/notification.service";
import {GameType} from "../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-statistic-card',
    templateUrl: './statistic-card.component.html',
    styleUrls: ['./statistic-card.component.scss']
})
export class StatisticCardComponent implements OnInit {

    title: string = '- podsumowanie';
    activeTab: GameType = GameType.NONE;
    profile: AccountModel;
    GameType = GameType;

    nav1 = document.getElementById('nav1');
    nav2 = document.getElementById('nav2');
    nav3 = document.getElementById('nav3');

    constructor(public appService: AppService,
                private profileService: ProfileService,
                private notificationService: NotificationService) {
        this.profile = appService.accountModel;
    }

    ngOnInit() {
        if (!this.appService.isLoggedIn()) {
            this.appService.logOut();
        }
    }

    changeTab(type: GameType) {
        this.activeTab = type;

        // this.nav1.classList.remove('active');
        // this.nav2.classList.remove('active');
        // this.nav3.classList.remove('active');

        switch (type) {
            case GameType.NONE: {
                this.title = '- podsumowanie';
                // this.nav1.classList.add('active');
                break;
            }
            case GameType.PVP: {
                this.title = 'przeciwko innym graczom';
                // this.nav2.classList.add('active');
                break;
            }
            case GameType.PVE: {
                this.title = 'przeciwko komputerowi';
                // this.nav3.classList.add('active');
                break;
            }
        }
    }
}
