import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Field} from '../../../../models/chess/field.model';
import {Piece} from '../../../../models/pieces/piece';
import {Move} from '../../../../models/chess/move';
import {AppService} from "../../../../services/app.service";
import {FieldSize} from "../../../../models/chess/field-size.eum";
import {GameType} from "../../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-board',
    templateUrl: './board.component.html',
    styleUrls: ['./board.component.scss']
})
export class BoardComponent {

    @Input() fields: Array<Field> = [];
    @Input() type: GameType;
    @Input() size: FieldSize = FieldSize.BIG;
    @Input() isPlayerPlaying: boolean = false;
    @Input() isGameContinued: boolean = false;

    currentPiece?: Piece;

    oldFieldDragged: Field = null;
    oldFieldSelected: Field = null;

    @Output() moveEventEmitter: EventEmitter<Move> = new EventEmitter();

    constructor(public baseService: AppService) {
    }

    move(field: Field) {
        this.moveEventEmitter.emit(this.prepareMove(this.oldFieldDragged.id, field.id));
        this.oldFieldDragged = null;
        this.oldFieldSelected = null;
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
        return new Move(source, destination, null, null, null, false);
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
