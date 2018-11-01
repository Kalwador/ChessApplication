import {Piece} from './piece.model';

export class Bishop extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WB.gif');
        } else {
            super('BB.gif');
        }
    }
}
