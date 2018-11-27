import {Component, Input, OnInit} from '@angular/core';
import {GamePveModel} from "../../../../models/chess/game/game-pve.model";
import {GamePvp} from "../../../../models/chess/game/game-pvp";
import {GameType} from "../../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-panel',
    templateUrl: './game-panel.component.html',
    styleUrls: ['./game-panel.component.css']
})
export class GamePanelComponent implements OnInit {

    @Input() gameType: GameType;

    @Input() gamePvE: GamePveModel;
    @Input() gamePvP: GamePvp;

    GameType = GameType;

    constructor() {
    }

    ngOnInit() {
    }

}
