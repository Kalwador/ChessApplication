import {Component, Input, OnInit} from '@angular/core';
import {PlayerColor} from "../../../../models/chess/player-color.enum";

@Component({
    selector: 'player-header',
    templateUrl: './player-header.component.html',
    styleUrls: ['./player-header.component.scss']
})
export class PlayerHeaderComponent implements OnInit {

    @Input() data: string;
    @Input() color: PlayerColor;
    PlayerColor = PlayerColor;

    @Input() whiteStatus: string = "";
    @Input() blackStatus: string = "";

    constructor() {
    }

    ngOnInit() {
    }

}
