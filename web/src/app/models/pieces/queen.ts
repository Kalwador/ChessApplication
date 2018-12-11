import {Piece} from './piece';

export class Queen extends Piece {
    constructor(isWhite: boolean) {
        if (isWhite) {
            super('WQ.gif');
        } else {
            super('BQ.gif');
        }
    }
}