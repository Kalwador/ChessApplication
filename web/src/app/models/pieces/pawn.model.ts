import {Piece} from './piece.model';

export class Pawn extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('');
        } else {
            super('');
        }
    }
}