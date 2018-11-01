import {Piece} from '../pieces/piece.model';

export class Field {
    id: number;
    isWhite: boolean;
    piece: Piece;

    constructor(id: number, isWhite: boolean, piece: Piece) {
        this.id = id;
        this.isWhite = isWhite;
        this.piece = piece;
    }
}
