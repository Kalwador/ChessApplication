import {Component, OnInit} from '@angular/core';
import {GamePve} from '../../../../models/game/game-pve';
import {ActivatedRoute} from '@angular/router';
import {GameService} from '../../service/game.service';
import {Field} from '../../../../models/game/field.model';
import {Move} from '../../../../models/game/move';
import {GameStatus} from '../../../../models/game/game-status.enum';
import {Queen} from '../../../../models/pieces/queen.model';

@Component({
    selector: 'app-game-play',
    templateUrl: './game-play-pve.component.html',
    styleUrls: ['./game-play-pve.component.css']
})
export class GamePlayPveComponent implements OnInit {

    game: GamePve;
    fields: Array<Field>;
    legateMoves: Array<Move>;

    constructor(private route: ActivatedRoute,
                private gameService: GameService) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.gameService.getPVEGame(+params['gameId']).subscribe(data => {
                this.game = data;
                this.fields = this.gameService.createBoard(this.game.board);
                if (this.game.status === GameStatus.READY) {
                    this.executeComputerFirstMove();
                }
                this.getLegateMoves();
            }, error => {
                if (error.status === 400) {
                    console.log('Wystąpił błąd, nie odnaleziono gry ');
                    //TODO-NOTIF_SERVICE Wystąpił błąd, nie odnaleziono gry.
                }
                if (error.status === 423) {
                    this.game.status = GameStatus.OVER;
                    console.log('koniec gry');
                    //TODO-NOTIF_SERVICE
                }
            });
        });
    }

    private executeComputerFirstMove() {
        this.gameService.getFirstMove(this.game.gameId).subscribe(data => {
            this.executeMove(data);
            this.game.status = GameStatus.PLAYER_MOVE;
        });
    }

    makeMove(move: Move) {
        if (this.game.status === GameStatus.PLAYER_MOVE || this.game.status === GameStatus.ON_HOLD) {
            let legateMove = this.getLegateMove(move.source, move.destination);
            if (legateMove !== null) {
                console.log('Player Move is Legate');
                this.executeMove(move);
                this.isSpecialMove(legateMove);

                this.gameService.makeMove(move, this.game.gameId).subscribe(data => {
                    console.log('Computer Move');
                    console.log(data.type);
                    this.executeMove(data);
                    this.isSpecialMove(data);
                    this.getLegateMoves();
                }, error => {
                    if (error.status === 423) {
                        console.log('koniec gry');
                        //TODO-NOTIF_SERVICE "Gra zakończona"
                    }
                });
            } else {
                //TODO - handle not legate move

            }
        }
    }

    private executeMove(move: Move) {
        console.log('Execute move ' + move.source + ' -> ' + move.destination);
        this.fields[move.destination].piece = this.fields[move.source].piece;
        this.fields[move.source].piece = null;
    }

    private getLegateMoves() {
        this.gameService.getLegateMoves(this.game.gameId).subscribe(data => {
            this.legateMoves = data;
            console.log('Legate Moves');
            console.log(this.legateMoves);
        });
    }

    private getLegateMove(source: number, destination: number): Move {
        for (let move of this.legateMoves) {
            if (move.source === source && move.destination === destination) {
                return move;
            }
        }
        return null;
    }

    private isSpecialMove(move: Move) {
        switch (move.type) {
            case 'KingSideCastleMove': {
                switch (move.destination) {
                    //BLACK
                    case 6: {
                        this.fields[5].piece = this.fields[7].piece;
                        this.fields[7].piece = null;
                        break;
                    }
                    //WHITE
                    case 62: {
                        this.fields[61].piece = this.fields[63].piece;
                        this.fields[63].piece = null;
                        break;
                    }
                }
                break;
            }
            case 'QueenSideCastleMove': {
                switch (move.destination) {
                    //BLACK
                    case 2: {
                        this.fields[3].piece = this.fields[0].piece;
                        this.fields[0].piece = null;
                        break;
                    }
                    //WHITE
                    case 58: {
                        this.fields[59].piece = this.fields[56].piece;
                        this.fields[59].piece = null;
                        break;
                    }
                }
                break;
            }
            case 'PawnPromotion': {
                //BLACK
                if (move.destination < 8) {
                    this.fields[move.destination].piece = new Queen(true);
                }
                //WHITE
                if (move.destination > 55) {
                    this.fields[move.destination].piece = new Queen(false);
                }
                break;
            }
            //TODO - bicie w przelocie
        }
    }
}
