import {Injectable, Query} from '@angular/core';
import {BaseService} from '../../../services/base.service';
import {GamePve} from '../../../models/game/game-pve';
import {Observable} from 'rxjs';
import {Field} from '../../../models/game/field.model';
import {Pawn} from '../../../models/pieces/pawn.model';
import {Rook} from '../../../models/pieces/rook.model';
import {Knight} from '../../../models/pieces/knight.model';
import {Bishop} from '../../../models/pieces/bishop.model';
import {Queen} from '../../../models/pieces/queen.model';
import {King} from '../../../models/pieces/king.model';
import {Move} from '../../../models/game/move';

@Injectable({
    providedIn: 'root'
})
export class GameService {

    blackPieces = ['p', 'r', 'n', 'b', 'q', 'k'];
    whitePieces = ['P', 'R', 'N', 'B', 'Q', 'K'];
    pathPvE = '/game/pve';

    constructor(private baseService: BaseService) {
    }

    public newPvE(color: string, level: number): Observable<number> {
        return this.baseService.mapJSON(this.baseService.post(this.pathPvE + '/new', new GamePve(color, level)));
    }

    public getPVEGame(gameId: number): Observable<GamePve> {
        return this.baseService.mapJSON(this.baseService.get(this.pathPvE + '/' + gameId));
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

    public makeMove(move: Move, id: number): Observable<Move> {
        return this.baseService.mapJSON(this.baseService.post(this.pathPvE + '/' + id, move));
    }

    getFirstMove(gameId: number): Observable<Move> {
        return this.baseService.mapJSON(this.baseService.get(this.pathPvE + '/' + gameId + '/first'));
    }

    public getPieceByChar(char: string, isWhite: boolean) {
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

    getLegateMoves(gameId: number) {
        return this.baseService.mapJSON(this.baseService.get(this.pathPvE + '/' + gameId + '/legate'));
    }
}