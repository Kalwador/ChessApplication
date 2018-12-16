import {Piece} from './piece';

export class Rook extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WR.gif');
        } else {
            super('BR.gif');
        }
    }
}