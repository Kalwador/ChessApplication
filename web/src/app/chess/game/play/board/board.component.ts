import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Field} from '../../../../models/chess/field.model';
import {Piece} from '../../../../models/pieces/piece.model';
import {Move} from '../../../../models/chess/move';
import {BaseService} from "../../../../services/base.service";

@Component({
    selector: 'app-board',
    templateUrl: './board.component.html',
    styleUrls: ['./board.component.scss']
})
export class BoardComponent {

    @Input() fields: Array<Field> = [];
    @Input() isPlayable: boolean = true;
    currentPiece?: Piece;

    oldFieldDragged: Field = null;
    oldFieldSelected: Field = null;

    @Output() moveEventEmitter: EventEmitter<Move> = new EventEmitter();

    constructor(public baseService: BaseService) {
    }

    move(field: Field) {
        this.moveEventEmitter.emit(this.prepareMove(this.oldFieldDragged.id, field.id));
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
        return new Move(source, destination, null, null, null);
    }

    selectField(field: Field) {
        if (this.oldFieldSelected == null) {
            this.oldFieldSelected = field;
        } else {
            this.moveEventEmitter.emit(this.prepareMove(this.oldFieldSelected.id, field.id));
            this.oldFieldSelected = null;
        }
    }

}
