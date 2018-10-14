import {Piece} from './piece.model';

export class King extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('');
        } else {
            super('');
        }
    }
}