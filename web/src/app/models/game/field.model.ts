import {Piece} from '../pieces/piece.model';

export class Field {
    isWhite: boolean;
    piece: Piece;

    constructor(isWhite: boolean, piece: Piece) {
        this.isWhite = isWhite;
        this.piece = piece;
    }

}