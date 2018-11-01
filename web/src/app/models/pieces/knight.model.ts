import {Piece} from './piece.model';

export class Knight extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WN.gif');
        } else {
            super('BN.gif');
        }
    }
}
