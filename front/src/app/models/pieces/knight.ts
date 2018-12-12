import {Piece} from './piece';

export class Knight extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WN.gif');
        } else {
            super('BN.gif');
        }
    }
}
