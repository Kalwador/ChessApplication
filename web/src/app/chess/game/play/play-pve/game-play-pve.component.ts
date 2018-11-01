import {Component, OnInit} from '@angular/core';
import {GamePve} from '../../../../models/game/game-pve';
import {ActivatedRoute} from '@angular/router';
import {GameService} from '../../service/game.service';
import {Field} from '../../../../models/game/field.model';
import {Move} from '../../../../models/game/move';
import {GameStatus} from '../../../../models/game/game-status.enum';

@Component({
    selector: 'app-game-play',
    templateUrl: './game-play-pve.component.html',
    styleUrls: ['./game-play-pve.component.css']
})
export class GamePlayPveComponent implements OnInit {

    game: GamePve;
    fields: Array<Field>;

    constructor(private route: ActivatedRoute,
                private gameService: GameService) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.gameService.getPVEGame(+params['gameId']).subscribe(data => {
                this.game = data;
                this.fields = this.gameService.createBoard(this.game.board);
                if (this.game.status === GameStatus.READY) {
                    this.gameService.getFirstMove(this.game.gameId).subscribe(data => {
                        this.executeMove(data);
                        this.game.status = GameStatus.PLAYER_MOVE;
                    });
                }
            }, error => {
                if (error.status === 423) {
                    this.game.status = GameStatus.OVER;
                    console.log('koniec gry');
                    //TODO-NOTIF_SERVICE
                }
            });
        });
    }

    makeMove(move: Move) {
        if (this.game.status === GameStatus.PLAYER_MOVE || this.game.status === GameStatus.ON_HOLD) {
            //if move is legate
            console.log('Player Move');
            this.executeMove(move);

            this.gameService.makeMove(move, this.game.gameId).subscribe(data => {
                console.log('Computer Move');
                if (data.type === 'CastleMove' || data.type === 'PawnPromotion') {
                    //reload game
                } else {
                    this.executeMove(data);
                }
            }, error => {
                if (error.status === 423) {
                    console.log('koniec gry');
                    //TODO-NOTIF_SERVICE
                }
            });
        }
    }

    private executeMove(move: Move) {
        console.log('Execute move ' + move.source + ' -> ' + move.destination);
        this.fields[move.destination].piece = this.fields[move.source].piece;
        this.fields[move.source].piece = null;
    }

    private reloadGame(){
        this.gameService.getPVEGame(+params['gameId']).subscribe(data => {
            this.game = data;
            this.fields = this.gameService.createBoard(this.game.board);
        });

    }
}
