import {Component, Input, OnInit} from '@angular/core';
import {GameType} from "../../../../models/chess/game/game-type.enum";
import {GamePvp} from "../../../../models/chess/game/game-pvp";
import {GamePve} from "../../../../models/chess/game/game-pve";
import {Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {NotificationService} from "../../../notifications/notification.service";

@Component({
    selector: 'game-list-table',
    templateUrl: './game-list-table.component.html',
    styleUrls: ['./game-list-table.component.scss']
})
export class GameListTableComponent implements OnInit {

    @Input() listSize: number = 7;
    @Input() type: GameType = null;

    gamesPvPList: Array<GamePvp>;
    gamesPvEList: Array<GamePve>;

    currentPvPPage: number = 0;
    currentPvEPage: number = 0;

    GameType = GameType;

    constructor(private router: Router,
                private service: GameService,
                private notif: NotificationService) {
    }

    ngOnInit() {
        if (this.type === GameType.PVP) {
            this.loadPvPGamesLists(this.currentPvPPage);
        }
        if (this.type === GameType.PVE) {
            this.loadPvEGamesLists(this.currentPvEPage);
        }
    }

    loadPvPGamesLists(page: number) {
        this.service.getPvPList(page, this.listSize).subscribe(data => {
            this.gamesPvPList = data.content;
            console.log(this.gamesPvPList);
        }, error => {
            this.notif.trace(error);
        });
    }

    loadPvEGamesLists(page: number) {
        this.service.getPvEList(page, this.listSize).subscribe(data => {
            this.gamesPvEList = data.content;
            console.log(this.gamesPvEList);
        }, error => {
            this.notif.trace(error);
        });
    }

}
