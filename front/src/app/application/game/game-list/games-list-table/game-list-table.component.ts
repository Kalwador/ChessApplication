import {Component, Input, OnInit} from '@angular/core';
import {GameType} from "../../../../models/chess/game/game-type.enum";
import {GamePvp} from "../../../../models/chess/game/game-pvp";
import {GamePve} from "../../../../models/chess/game/game-pve";
import {ActivatedRoute, Router} from "@angular/router";
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
                private route: ActivatedRoute,
                private service: GameService,
                private notif: NotificationService) {
        this.route.params.subscribe(params => {
            if(params['type'] === 'pve') {
                this.type = GameType.PVE;
            }
            if(params['type'] === 'pvp') {
                this.type = GameType.PVP;
            }
        });
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
        }, error => {
            this.notif.trace(error);
        });
    }

    loadPvEGamesLists(page: number) {
        this.service.getPvEList(page, this.listSize).subscribe(data => {
            this.gamesPvEList = data.content;
        }, error => {
            this.notif.trace(error);
        });
    }

}
