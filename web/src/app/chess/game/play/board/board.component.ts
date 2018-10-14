import {Component, Input, OnInit} from '@angular/core';
import {Field} from '../../../../models/game/field.model';

@Component({
    selector: 'app-board',
    templateUrl: './board.component.html',
    styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

    @Input() fields: Array<Array<Field>>;

    constructor() {
    }

    ngOnInit() {
    }

}
