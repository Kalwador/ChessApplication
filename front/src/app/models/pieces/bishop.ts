import {Piece} from './piece';

export class Bishop extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WB.gif');
        } else {
            super('BB.gif');
        }
    }
}
