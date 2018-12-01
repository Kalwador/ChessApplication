import {Injectable} from "@angular/core";
import {GameStatus} from "../../../models/chess/game/game-status.enum";
import {Move} from "../../../models/chess/move";
import {Queen} from "../../../models/pieces/queen.model";
import {NotificationService} from "../../notifications/notification.service";
import {GameService} from "./game.service";
import {Field} from "../../../models/chess/field.model";
import {PlayerColor} from "../../../models/chess/player-color.enum";
import {st} from "../../../../../node_modules/@angular/core/src/render3";
import {GameType} from "../../../models/chess/game/game-type.enum";

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

    checkIfGameContinued(status): boolean {
        switch (status) {
            case GameStatus.PLAYER_MOVE: {
                this.getLegateMoves(GameType.PVE);
                return true;
            }
            case GameStatus.WHITE_MOVE:
            case GameStatus.BLACK_MOVE: {
                this.getLegateMoves(GameType.PVP);
                return true;
            }
            case GameStatus.ROOM: {
                this.notificationService.info("Oczekiwanie na drugiego gracza");
                return false;
            }
            case GameStatus.CHECK: {
                this.notificationService.info("Szach!");
                return true;
            }
            case GameStatus.DRAW: {
                this.notificationService.info('Gra skończona, remis');
                return false;
            }
            case GameStatus.BLACK_WIN: {
                this.notificationService.info('Gra skończona, wygrały czarne');
                return false;
            }
            case GameStatus.WHITE_WIN: {
                this.notificationService.info('Gra skończona, wygrały białe');
                return false;
            }
            default: {
                //TODO - do usuniecia
                this.notificationService.danger('!!! BARDZO ZLE - nic nie pasuje, status= ' + status);
                return false;
            }
        }
    }

    private getLegateMoves(type: GameType) {
        this.gameService.getLegateMoves(this.game.id, type).subscribe(data => {
            this.legateMoves = data;
        });
    }

    getLegateMove(source: number, destination: number): Move {
        for (let move of this.legateMoves) {
            if (move.source === source && move.destination === destination) {
                return move;
            }
        }
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
}