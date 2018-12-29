import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {GameService} from '../../service/game.service';
import {Move} from '../../../../models/chess/move';
import {GameStatus} from '../../../../models/chess/game/game-status.enum';
import {PlayerColor} from '../../../../models/chess/player-color.enum';
import {NotificationService} from '../../../notifications/notification.service';
import {CurrentGameService} from '../../service/current-game.service';
import {GameType} from '../../../../models/chess/game/game-type.enum';
import {FieldSize} from '../../../../models/chess/field-size.eum';

@Component({
    selector: 'app-game-play',
    templateUrl: './game-play-pve.component.html',
    styleUrls: ['./game-play-pve.component.scss']
})
export class GamePlayPveComponent implements OnInit {

    whiteStatus: string = "";
    whitePlayerNick: string;

    blackStatus: string = "";
    blackPlayerNick: string;

    isGameContinued: boolean;
    FieldSize = FieldSize;
    PlayerColor = PlayerColor;

    constructor(private route: ActivatedRoute,
                private gameService: GameService,
                public currentGameService: CurrentGameService,
                private notificationService: NotificationService,
                public router: Router) {
        this.route.params.subscribe(params => {
            this.gameService.getGame(+params['id'], GameType.PVE).subscribe(data => {
                this.currentGameService.game = data;
                this.currentGameService.fields = this.gameService.createBoard(this.currentGameService.game.board);
                this.isGameContinued = this.currentGameService.checkIfGameContinued(this.currentGameService.game.status, true);
                this.handleGameStatus(this.currentGameService.game.status, false);
                if (this.isGameContinued) {
                    this.currentGameService.getLegateMoves(GameType.PVE);
                }
                this.setPlayersInfo();
            }, error => {
                if (error.status === 400) {
                    this.router.navigate(['/']);
                    this.notificationService.warning('Wystąpił błąd, nie odnaleziono gry');
                }
            });
        });
    }

    ngOnInit() {

    }

    makeMove(move: Move) {
        if (this.currentGameService.game.status === GameStatus.PLAYER_MOVE
            || this.currentGameService.game.status === GameStatus.ON_HOLD
            || this.currentGameService.game.status === GameStatus.CHECK) {
            let legateMove = this.currentGameService.getLegateMove(move.source, move.destination);
            if (legateMove !== null) {
                if (this.currentGameService.game.status != GameStatus.CHECK) {
                    this.currentGameService.executeMove(move);
                }
                this.gameService.makeMove(move, this.currentGameService.game.id, GameType.PVE).subscribe(data => {
                    if (this.currentGameService.checkIfGameContinued(data.status, true)) {
                        this.currentGameService.getLegateMoves(GameType.PVE);
                    }
                    if (this.currentGameService.game.status === GameStatus.CHECK) {
                        this.currentGameService.executeMove(move);
                    }
                    this.currentGameService.executeMove(data);
                    this.handleGameStatus(this.currentGameService.game.status, false);
                    //TODO replace log
                }, error => {
                    if (error.status === 400) {
                        this.notificationService.info('Błędny ruch');
                    }
                    if (error.status === 423) {
                        this.notificationService.info('Gra zakończona');
                    }
                });
            } else {
                this.notificationService.info('Błędny ruch');
            }
        }
    }

    private setPlayersInfo() {
        if (this.currentGameService.game.color === PlayerColor.WHITE) {
            this.whitePlayerNick = this.gameService.getAccountModel().nick;
            this.blackPlayerNick = 'Komputer poziom: ' + this.currentGameService.game.level;
        } else {
            this.whitePlayerNick = 'Komputer poziom: ' + this.currentGameService.game.level;
            this.blackPlayerNick = this.gameService.getAccountModel().nick;
        }
    }

    private handleGameStatus(status: string, isInCheck: boolean) {
        let temp = '';
        if (isInCheck) {
            temp = "Szach! ";
        }
        if (status === GameStatus.WHITE_MOVE) {
            temp = temp + this.gameService.translateStatus(status);
            this.whiteStatus = temp;
            this.blackStatus = "";
        }
        if (status === GameStatus.BLACK_MOVE) {
            temp = temp + this.gameService.translateStatus(status);
            this.blackStatus = temp;
            this.whiteStatus = "";
        }
    }
}
