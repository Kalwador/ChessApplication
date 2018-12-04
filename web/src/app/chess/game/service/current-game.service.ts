import {Injectable} from "@angular/core";
import {GameStatus} from "../../../models/chess/game/game-status.enum";
import {Move} from "../../../models/chess/move";
import {Queen} from "../../../models/pieces/queen.model";
import {NotificationService} from "../../notifications/notification.service";
import {GameService} from "./game.service";
import {Field} from "../../../models/chess/field.model";
import {GameType} from "../../../models/chess/game/game-type.enum";
import {Observable} from "rxjs";
import {ChatModel} from "../../../models/chat.model";

@Injectable({
    providedIn: 'root'
})
export class CurrentGameService {

    game: any;
    legateMoves: Array<Move>;
    fields: Array<Field>;

    constructor(private gameService: GameService,
                private notificationService: NotificationService) {
    }

    checkIfGameContinued(status, notify: boolean): boolean {
        switch (status) {
            case GameStatus.PLAYER_MOVE: {
                return true;
            }
            case GameStatus.WHITE_MOVE:
            case GameStatus.BLACK_MOVE: {
                return true;
            }
            case GameStatus.ROOM: {
                if (notify === true) this.notificationService.info("Oczekiwanie na drugiego gracza");
                return false;
            }
            case GameStatus.CHECK: {
                if (notify === true) this.notificationService.info("Szach!");
                return true;
            }
            case GameStatus.ON_HOLD: {
                return true;
            }
            case GameStatus.BLACK_WIN: {
                if (notify === true) this.notificationService.info('Gra skończona, wygrały czarne');
                return false;
            }
            case GameStatus.WHITE_WIN: {
                if (notify === true) this.notificationService.info('Gra skończona, wygrały białe');
                return false;
            }
            case GameStatus.DRAW: {
                if (notify === true) this.notificationService.info('Gra skończona, remis');
                return false;
            }
            default: {
                if (notify === true) this.notificationService.danger('!!! BARDZO ZLE - nic nie pasuje, status= ' + status);
                return false;
            }
        }
    }

    public getLegateMoves(type: GameType) {
        this.gameService.getLegateMoves(this.game.id, type).subscribe(data => {
            this.legateMoves = data;
        });
    }

    getLegateMove(source: number, destination: number): Move {
        console.log("4.1");
        for (let move of this.legateMoves) {
            if (move.source === source && move.destination === destination) {
                return move;
            }
        }
        console.log(4.9);
        return null;
    }

    private isSpecialMove(type: string) {
        return type === 'KingSideCastleMove'
            || type === 'QueenSideCastleMove'
            || type === 'PawnPromotion';
        //TODO - bicie w przelocie
    }

    private specialMoveExecutor(move: Move) {
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

    executeMove(move: Move) {
        this.fields[move.destination].piece = this.fields[move.source].piece;
        this.fields[move.source].piece = null;
        if (this.isSpecialMove(move.type)) {
            this.specialMoveExecutor(move);
        }
    }

    getChatConversation(): Observable<ChatModel> {
        return this.gameService.getChatConversation(this.game.id);
    }
}