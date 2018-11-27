import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../service/game.service";
import {NotificationService} from "../../notifications/notification.service";
import {GamePvp} from "../../../models/chess/game/game-pvp";
import {GamePveModel} from "../../../models/chess/game/game-pve.model";
import {GameType} from "../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-games',
    templateUrl: './game-list.component.html',
    styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {


    gamesPvPList: Array<GamePvp>;
    gamesPvEList: Array<GamePveModel>;

    currentPvPPage: number = 0;
    currentPvEPage: number = 0;

    defaultSize: number = 3;

    GameType = GameType;

    constructor(private router: Router,
                private service: GameService,
                private notif: NotificationService) {

        this.loadPvPGamesLists(this.currentPvPPage);
        this.loadPvEGamesLists(this.currentPvEPage);

    }

    ngOnInit() {
    }

    newGame() {
        this.router.navigate(['/game/new-options']);
    }

    loadPvPGamesLists(page: number) {
        this.service.getPvPList(page, this.defaultSize).subscribe(data => {
            this.gamesPvPList = data.content;
            console.log(this.gamesPvPList);
        }, error => {
            this.notif.trace(error);
        });
    }

    loadPvEGamesLists(page: number) {
        this.service.getPvEList(page, this.defaultSize).subscribe(data => {
            this.gamesPvEList = data.content;
            console.log(this.gamesPvEList);
        }, error => {
            this.notif.trace(error);
        });
    }
}
