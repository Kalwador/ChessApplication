import {Injectable} from '@angular/core';
import {AppService} from '../../../services/app.service';
import {Observable} from 'rxjs';
import {Field} from '../../../models/chess/field.model';
import {Pawn} from '../../../models/pieces/pawn';
import {Rook} from '../../../models/pieces/rook';
import {Knight} from '../../../models/pieces/knight';
import {Bishop} from '../../../models/pieces/bishop';
import {Queen} from '../../../models/pieces/queen';
import {King} from '../../../models/pieces/king';
import {Move} from '../../../models/chess/move';
import {AccountModel} from '../../../models/profile/account.model';
import {PageModel} from "../../../models/application/page.model";
import {GameType} from "../../../models/chess/game/game-type.enum";
import {NotificationService} from "../../notifications/notification.service";
import {GamePvpModel} from "../../../models/chess/game/game-pvp-model";
import {GameModel} from "../../../models/chess/game/game.model";
import {GameStatus} from "../../../models/chess/game/game-status.enum";
import {GamePveModel} from "../../../models/chess/game/game-pve-model";
import {ChatModel} from "../../../models/chat.model";

@Injectable({
    providedIn: 'root'
})
export class GameService {

    blackPieces = ['p', 'r', 'n', 'b', 'q', 'k'];
    whitePieces = ['P', 'R', 'N', 'B', 'Q', 'K'];
    public pathPvE = '/game/pve';
    public pathPvP = '/game/pvp';


    constructor(private appService: AppService,
                private notificationService: NotificationService) {
    }

    public newPvE(game: GamePveModel): Observable<number> {
        return this.appService.post(this.pathPvE + '/new', game);
    }

    public newPvP(game: GamePvpModel): Observable<number> {
        return this.appService.post(this.pathPvP + '/new', game);
    }

    public findPvP(game: GamePvpModel): Observable<number> {
        return this.appService.post(this.pathPvP + '/find', game);
    }

    public getGame(gameId: number, type: GameType): Observable<GameModel> {
        let path: string = type === GameType.PVE ? this.pathPvE : this.pathPvP;
        path += '/' + gameId;
        this.notificationService.trace('get game, path: ' + path);
        return this.appService.get(path);
    }

    public createBoard(fenBoard: string): Array<Field> {
        let board = Array<Field>();
        let isActualColorWhite = true;
        let id = 0;
        let fen = fenBoard.split(' ')[0];

        for (let char of fen) {
            if (this.blackPieces.includes(char)) {
                board.push(new Field(id, isActualColorWhite, this.getPieceByChar(char, false)));
                isActualColorWhite = !isActualColorWhite;
                id = id + 1;
            } else if (this.whitePieces.includes(char)) {
                board.push(new Field(id, isActualColorWhite, this.getPieceByChar(char, true)));
                isActualColorWhite = !isActualColorWhite;
                id = id + 1;
            } else if (char === '/') {
                isActualColorWhite = !isActualColorWhite;
            } else {
                for (let i = 0; i < +char; i++) {
                    board.push(new Field(id, isActualColorWhite, null));
                    isActualColorWhite = !isActualColorWhite;
                    id = id + 1;
                }
            }
        }
        return board;
    }

    public makeMove(move: Move, id: number, type: GameType): Observable<Move> {
        let path: string = type === GameType.PVE ? this.pathPvE : this.pathPvP;
        return this.appService.post(path + '/' + id, move);
    }

    public getLegateMoves(gameId: number, type: GameType) {
        let path: string = type === GameType.PVE ? this.pathPvE : this.pathPvP;
        return this.appService.get(path + '/' + gameId + '/legate');
    }

    public getAccountModel(): AccountModel {
        return this.appService.accountModel;
    }

    public getPvPList(page: number, size: number): Observable<PageModel> {
        let paging = this.appService.getPaging(page, size);
        return this.appService.get(this.pathPvP + paging);
    }

    public getPvEList(page: number, size: number): Observable<PageModel> {
        let paging = this.appService.getPaging(page, size);
        return this.appService.get(this.pathPvE + paging);
    }

    private getPieceByChar(char: string, isWhite: boolean) {
        switch (char) {
            case 'p':
            case 'P':
                return new Pawn(isWhite);
            case 'r':
            case 'R':
                return new Rook(isWhite);
            case 'n':
            case 'N':
                return new Knight(isWhite);
            case 'b':
            case 'B':
                return new Bishop(isWhite);
            case 'q':
            case 'Q':
                return new Queen(isWhite);
            case 'k':
            case 'K':
                return new King(isWhite);
        }
    }

    public getBasePath(): string {
        return this.appService.getBasePath();
    }

    public translateStatus(status: string): string {
        switch (status) {
            case GameStatus.PLAYER_MOVE: {
                return " Twój ruch";
            }
            case GameStatus.BLACK_MOVE: {
                return "Ruch czarnych";
            }
            case GameStatus.WHITE_MOVE: {
                return "Ruch białych";
            }
            case GameStatus.ON_HOLD: {
                return "Gra zatrzymana";
            }
            case GameStatus.ROOM: {
                return "Oczekiwanie na drugieo gracza";
            }
            case GameStatus.CHECK: {
                return "Szach!";
            }
            case GameStatus.WHITE_WIN: {
                return "Wygrały czarne";
            }
            case GameStatus.BLACK_WIN: {
                return "Wygrały białe";
            }
            case GameStatus.DRAW: {
                return "Remis";
            }
            default: {
                return "";
            }
        }
    }

    public forfeit(gameId: number, type: GameType): Observable<string> {
        let path: string = type === GameType.PVE ? this.pathPvE : this.pathPvP;
        path += '/forfeit/' + gameId;
        this.notificationService.trace('forfeit game, path: ' + path);
        return this.appService.delete(path);
    }

    getChatConversation(id: number): Observable<ChatModel> {
        return this.appService.get(this.pathPvP + "/" + id + "/chat/");
    }
}