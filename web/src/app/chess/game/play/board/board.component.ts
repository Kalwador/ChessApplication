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
    oldFieldDragged: Field = null;
    oldFieldSelected: Field = null;

    @Output() emiter: EventEmitter<Move> = new EventEmitter();

    constructor() {
    }

    move(field: Field) {
        this.emiter.emit(this.prepareMove(this.oldFieldDragged.id, field.id));
    }

    remove(field: Field) {
        this.oldFieldDragged = field;
    }

    isNewLine(id: number) {
        if (id === 7 || id === 15 || id === 23 || id === 31 || id === 39 || id === 47 || id === 55) {
            return true;
        }
        return false;
    }

    prepareMove(source: number, destination: number): Move {
        return new Move(source, destination, null, null);
    }

    selectField(field: Field) {
        if (this.oldFieldSelected == null) {
            this.oldFieldSelected = field;
        } else {
            this.emiter.emit(this.prepareMove(this.oldFieldSelected.id, field.id));
            this.oldFieldSelected = null;
        }
    }

}
