import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Field} from '../../../../models/game/field.model';
import {Piece} from '../../../../models/pieces/piece.model';
import {Move} from '../../../../models/game/move';

@Component({
    selector: 'app-board',
    templateUrl: './board.component.html',
    styleUrls: ['./board.component.css']
})
export class BoardComponent {

    currentPiece?: Piece;
    @Input() fields: Array<Field> = [];
    oldField = null;

    @Output() emiter: EventEmitter<Move> = new EventEmitter();

    constructor() {
    }

    move(piece: Piece, field: Field) {
        this.emiter.emit(this.prepareMove(field.id));
    }

    remove(field: Field) {
        this.oldField = field;
    }

    isNewLine(id: number) {
        if (id === 7 || id === 15 || id === 23 || id === 31 || id === 39 || id === 47 || id === 55) {
            return true;
        }
        return false;
    }

    prepareMove(destination: number): Move {
        return new Move(this.oldField.id, destination);
    }
}
