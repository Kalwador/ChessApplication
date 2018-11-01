import {Piece} from './piece.model';

export class Rook extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WR.gif');
        } else {
            super('BR.gif');
        }
    }
}