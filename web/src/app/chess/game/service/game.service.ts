import {Injectable, Query} from '@angular/core';
import {BaseService} from '../../../services/base.service';
import {GamePve} from '../../../models/game/game-pve';
import {Observable} from 'rxjs';
import {Field} from '../../../models/game/field.model';
import {Piece} from '../../../models/pieces/piece.model';
import {Pawn} from '../../../models/pieces/pawn.model';
import {Rook} from '../../../models/pieces/rook.model';
import {Knight} from '../../../models/pieces/knight.model';
import {Bishop} from '../../../models/pieces/bishop.model';
import {Queen} from '../../../models/pieces/queen.model';
import {King} from '../../../models/pieces/king.model';

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

    public createBoard(fenBoard: string): Array<Array<Field>> {
        let board = Array<Array<Field>>();
        let isActualColorWhite = true;
        let fen = fenBoard.split(' ')[0].split('/');
        for (let fenRow of fen) {
            let fieldRow = [];
            for (let char of fenRow) {
                if (this.blackPieces.includes(char)) {
                    fieldRow.push(new Field(isActualColorWhite, this.getPieceByChar(char, false)));
                    isActualColorWhite = !isActualColorWhite;
                } else if (this.whitePieces.includes(char)) {
                    fieldRow.push(new Field(isActualColorWhite, this.getPieceByChar(char, true)));
                    isActualColorWhite = !isActualColorWhite;
                } else {
                    for (let i = 0; i < +char; i++) {
                        fieldRow.push(new Field(isActualColorWhite, null));
                        isActualColorWhite = !isActualColorWhite;
                    }
                }
            }
            board.push(fieldRow);
            isActualColorWhite = !isActualColorWhite;
        }
        return board;
    }

    public getPieceByChar(char: string, isWhite: boolean) {
        switch (char) {
            case 'p':
                return new Pawn(isWhite);
            case 'r':
                return new Rook(isWhite);
            case 'n':
                return new Knight(isWhite);
            case 'b':
                return new Bishop(isWhite);
            case 'q':
                return new Queen(isWhite);
            case 'k':
                return new King(isWhite);
        }
    }
}