import {Component, Input, OnInit} from '@angular/core';
import {StatisticsTypeEnum} from "../../../../models/statistics/statistics-type.enum";
import {StatisticsModel} from "../../../../models/profile/statistics.model";
import {GameType} from "../../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-statistics-header',
    templateUrl: './statistic-table.component.html',
    styleUrls: ['./statistic-table.component.scss']
})
export class StatisticTableComponent implements OnInit {

    @Input() statistics: StatisticsModel = null;
    @Input() type: GameType = null;
    GameType = GameType;

    constructor() {
    }

    ngOnInit() {

    }

}
