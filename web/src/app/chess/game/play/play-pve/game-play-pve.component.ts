import {Component, OnInit} from '@angular/core';
import {GamePve} from '../../../../models/game/game-pve';
import {ActivatedRoute, Router} from '@angular/router';
import {GameService} from '../../service/game.service';
import {Field} from '../../../../models/game/field.model';
import {Move} from '../../../../models/game/move';
import {GameStatus} from '../../../../models/game/game-status.enum';
import {Queen} from '../../../../models/pieces/queen.model';
import {PlayerColor} from '../../../../models/game/player-color.enum';

@Component({
    selector: 'app-game-play',
    templateUrl: './game-play-pve.component.html',
    styleUrls: ['./game-play-pve.component.scss']
})
export class GamePlayPveComponent implements OnInit {

    game: GamePve;
    fields: Array<Field>;
    legateMoves: Array<Move>;
    whitePlayerNick: string;
    blackPlayerNick: string;

    constructor(private route: ActivatedRoute,
                private gameService: GameService,
                public router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.gameService.getPVEGame(+params['gameId']).subscribe(data => {
                this.game = data;
                this.fields = this.gameService.createBoard(this.game.board);
                this.isGameContinued(this.game.status);
                if(this.game.color === PlayerColor.WHITE) {
                    this.whitePlayerNick = this.gameService.getAccountModel().username;
                    this.blackPlayerNick = 'Computer Lv: ' + this.game.level;
                } else {
                    this.whitePlayerNick = 'Computer Lv: ' + this.game.level;
                    this.blackPlayerNick = this.gameService.getAccountModel().username;
                }
            }, error => {
                if (error.status === 400) {
                    this.router.navigate(['/']);
                    console.log('Wystąpił błąd, nie odnaleziono gry ');
                    //TODO-NOTIF_SERVICE
                }
            });
        });
    }

    makeMove(move: Move) {
        if (this.game.status === GameStatus.PLAYER_MOVE || this.game.status === GameStatus.ON_HOLD || this.game.status === GameStatus.CHECK) {
            let legateMove = this.getLegateMove(move.source, move.destination);
            if (legateMove !== null) {
                if (this.game.status != GameStatus.CHECK) {
                    this.executeMove(move);
                }

                this.gameService.makeMove(move, this.game.gameId).subscribe(data => {
                    if (this.isGameContinued(data.status)) {
                        if (this.game.status === GameStatus.CHECK) {
                            this.executeMove(move);
                        }
                        this.executeMove(data);
                    }
                    this.game.status = data.status;
                    //TODO replace log

                }, error => {
                    if (error.status === 400) {
                        console.log('bledny ruch');
                        //TODO-NOTIF_SERVICE
                    }
                    if (error.status === 423) {
                        console.log('koniec gry');
                        //TODO-NOTIF_SERVICE
                    }
                });
            } else {
                console.log('bledny ruch');
                //TODO-NOTIF_SERVICE
            }
        }
    }


    private executeMove(move: Move) {
        this.fields[move.destination].piece = this.fields[move.source].piece;
        this.fields[move.source].piece = null;
        this.isSpecialMove(move);
    }

    private getLegateMoves() {
        this.gameService.getLegateMoves(this.game.gameId).subscribe(data => {
            this.legateMoves = data;
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

    private isGameContinued(status: string): boolean {
        switch (status) {
            //TODO-NOTIF_SERVICE
            case GameStatus.CHECK: {
                console.log('Szach');
                return true;
            }
            case GameStatus.DRAW: {
                console.log('Gra skończona, remis');
                return false;
            }
            case GameStatus.BLACK_WIN: {
                console.log('Gra skończona, wygrały czarne');
                return false;
            }
            case GameStatus.WHITE_WIN: {
                console.log('Gra skończona, wygrały białe');
                return false;
            }
            case GameStatus.PLAYER_MOVE: {
                this.getLegateMoves();
                return true;
            }
            default: {
                //TODO - do usuniecia
                console.log('!!! BARDZO ZLE - nic nie pasuje, status= ' + status);
                return true;
            }
        }
    }
}
