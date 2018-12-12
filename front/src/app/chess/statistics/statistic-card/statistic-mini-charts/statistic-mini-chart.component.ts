import {Component, Input, OnInit} from '@angular/core';
import {StatisticsModel} from "../../../../models/profile/statistics.model";
import {GameType} from "../../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-statistic-mini-chart',
    templateUrl: './statistic-mini-chart.component.html',
    styleUrls: ['./statistic-mini-chart.component.scss']
})
export class StatisticMiniChartComponent implements OnInit {

    @Input() chartType: string = 'doughnut';
    @Input() type: GameType = null;
    @Input() statistics: StatisticsModel = null;

    GameType = GameType;
    public chartDatasets1: Array<any>;
    public chartDatasets2: Array<any>;
    public chartDatasets3: Array<any>;
    public chartDatasets4: Array<any>;
    public chartDatasets5: Array<any>;
    public chartDatasets6: Array<any>;


    constructor() {
    }

    ngOnInit() {
        this.chartDatasets1 = [
            {
                data: [this.statistics.gamesPvE, this.statistics.gamesPvP],
                label: 'Wszystkie gry'
            }
        ];
        this.chartDatasets2 = [
            {
                data: [this.statistics.winGamesPvE, this.statistics.winGamesPvP],
                label: 'Wszystkie gry'
            }
        ];
        this.chartDatasets3 = [
            {
                data: [this.statistics.monthGamesPvE, this.statistics.monthGamesPvP],
                label: 'Wszystkie gry'
            }
        ];
        this.chartDatasets4 = [
            {
                data: [this.statistics.monthWinGamesPvE, this.statistics.monthWinGamesPvP],
                label: 'Wszystkie gry'
            }
        ];
        this.chartDatasets5 = [
            {
                data: [this.statistics.weekGamesPvE, this.statistics.weekGamesPvP],
                label: 'Wszystkie gry'
            }
        ];
        this.chartDatasets6 = [
            {
                data: [this.statistics.weekWinGamesPvE, this.statistics.weekWinGamesPvP],
                label: 'Wszystkie gry'
            }
        ];
    }

    public chartLabels: Array<any> = ['PvP', 'PvE'];

    public chartColors1: Array<any> = [
        {
            backgroundColor: ['#F7464A', '#46BFBD'],
            hoverBackgroundColor: ['#FF5A5E', '#5AD3D1'],
            borderWidth: 2,
        }
    ];

    public chartColors2: Array<any> = [
        {
            backgroundColor: ['#00e30e', '#bf8300'],
            hoverBackgroundColor: ['#00ff31', '#d3aa11'],
            borderWidth: 2,
        }
    ];


    public chartOptions: any = {
        responsive: true,
        legend: {
            labels: {
                fontSize: 15,
                fontColor: 'white',
            }
        }
    };

    public chartClicked(e: any): void {
    }

    public chartHovered(e: any): void {
    }
}
