import {Component, Input, OnInit} from '@angular/core';
import {GamePve} from "../../../../models/chess/game/game-pve";
import {GamePvp} from "../../../../models/chess/game/game-pvp";
import {GameType} from "../../../../models/chess/game/game-type.enum";
import {Field} from "../../../../models/chess/field.model";
import {GameService} from "../../service/game.service";
import {FieldSize} from "../../../../models/chess/field-size.eum";
import {ProfileService} from "../../../profile/profile-service/profile.service";


@Component({
    selector: 'app-panel',
    templateUrl: './game-panel.component.html',
    styleUrls: ['./game-panel.component.scss']
})
export class GamePanelComponent implements OnInit {

    @Input() gameType: GameType;
    @Input() gamePvE: GamePve;
    @Input() gamePvP: GamePvp;

    fields: Array<Field>;

    whitePlayerNick: string = '';
    blackPlayerNick: string = '';

    computerName:string = null;

    status: string = null;

    GameType = GameType;
    FieldSize = FieldSize;

    constructor(
        private gameService: GameService,
        private profileService: ProfileService) {
    }

    ngOnInit() {
        if (this.gameType === GameType.PVP) {
            this.fields = this.gameService.createBoard(this.gamePvP.board);

            if (this.gamePvP.black != null) {
                this.profileService.getNickById(Number(this.gamePvP.black)).subscribe(data => {
                    this.blackPlayerNick = data;
                });
            }
            if (this.gamePvP.white != null) {
                this.profileService.getNickById(Number(this.gamePvP.white)).subscribe(data => {
                    this.whitePlayerNick = data;
                });
            }
            this.status =  this.gameService.translateStatus(this.gamePvP.status);

            console.log(this.gamePvP);
        } else {
            this.fields = this.gameService.createBoard(this.gamePvE.board);
            this.computerName = 'Computer level: ' + this.gamePvE.level;
            this.status = this.gameService.translateStatus(this.gamePvE.status);

            console.log(this.gamePvE);
        }
    }

    private setUpNicksPvP(nick: string): boolean {
        return this.profileService.getUserProfile().nick === nick;
    }

    enterToGame() {

    }
}
