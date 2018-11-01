import {Piece} from './piece.model';

export class King extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WK.gif');
        } else {
            super('BK.gif');
        }
    }
}