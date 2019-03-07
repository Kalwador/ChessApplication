import {Component, Input, OnInit} from '@angular/core';
import {GamePveModel} from '../../../../models/chess/game/game-pve-model';
import {GamePvpModel} from '../../../../models/chess/game/game-pvp-model';
import {GameType} from '../../../../models/chess/game/game-type.enum';
import {Field} from '../../../../models/chess/field.model';
import {GameService} from '../../service/game.service';
import {FieldSize} from '../../../../models/chess/field-size.eum';
import {ProfileService} from '../../../profile/profile-service/profile.service';
import {PlayerColor} from '../../../../models/chess/player-color.enum';
import {Router} from '@angular/router';
import {NotificationService} from "../../../notifications/notification.service";
import {CurrentGameService} from "../../service/current-game.service";


@Component({
    selector: 'app-game-list-panel',
    templateUrl: './game-panel.component.html',
    styleUrls: ['./game-panel.component.scss']
})
export class GamePanelComponent implements OnInit {

    @Input() gameType: GameType;
    @Input() gamePvE: GamePveModel;
    @Input() gamePvP: GamePvpModel;

    fields: Array<Field>;

    whitePlayerNick: string = '---';
    blackPlayerNick: string = '---';

    status: string = null;

    GameType = GameType;
    FieldSize = FieldSize;

    isGameContinued: boolean = true;

    constructor(
        private gameService: GameService,
        private profileService: ProfileService,
        private notificationService: NotificationService,
        private currentGameService: CurrentGameService,
        private router: Router) {
    }

    ngOnInit() {
        if (this.gameType === GameType.PVE) {
            this.isGameContinued = this.currentGameService.checkIfGameContinued(this.gamePvE.status, false);
            this.status = this.gameService.translateStatus(this.gamePvE.status);
            this.fields = this.gameService.createBoard(this.gamePvE.board);
            this.profileService.getUserProfile().then(data => {
                    if (this.gamePvE.color === PlayerColor.WHITE) {
                        this.whitePlayerNick = data.nick;
                        this.blackPlayerNick = 'Komputer poziom: ' + this.gamePvE.level;
                    } else {
                        this.whitePlayerNick = 'Komputer poziom: ' + this.gamePvE.level;
                        this.blackPlayerNick = data.nick;
                    }
                }
            );
        } else {
            this.status = this.gameService.translateStatus(this.gamePvP.status);
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
        }
    }

    enterToGame() {
        if (this.gameType === GameType.PVE) {
            this.router.navigate(['/game/play/pve', this.gamePvE.id]);
        } else {
            this.router.navigate(['/game/play/pvp', this.gamePvP.id]);
        }
    }

    forfeitGame() {
        if (this.gameType === GameType.PVE) {
            this.gameService.forfeit(this.gamePvE.id, GameType.PVE).subscribe(data => {
                this.notificationService.info("Gra porzucona");
            });
        } else {
            this.gameService.forfeit(this.gamePvP.id, GameType.PVP).subscribe(data => {
                this.notificationService.info("Gra porzucona");
            });
        }
    }
}
