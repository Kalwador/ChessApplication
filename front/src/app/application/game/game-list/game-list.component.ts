import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../service/game.service";
import {NotificationService} from "../../notifications/notification.service";
import {GameType} from "../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-game-list',
    templateUrl: './game-list.component.html',
    styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

    defaultSize: number = 3;
    GameType = GameType;

    constructor(private router: Router,
                private service: GameService,
                private notif: NotificationService) {
    }

    ngOnInit() {
    }
}
