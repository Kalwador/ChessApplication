import {Piece} from './piece';

export class Pawn extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WP.gif');
        } else {
            super('BP.gif');
        }
    }
}
