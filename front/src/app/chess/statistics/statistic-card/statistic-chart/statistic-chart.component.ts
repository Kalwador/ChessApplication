import {Component, Input, OnInit} from '@angular/core';
import {StatisticsModel} from "../../../../models/profile/statistics.model";
import {GameType} from "../../../../models/chess/game/game-type.enum";

@Component({
    selector: 'app-statistic-chart',
    templateUrl: './statistic-chart.component.html',
    styleUrls: ['./statistic-chart.component.scss']
})
export class StatisticChartComponent implements OnInit {

    @Input() chartType: string = 'horizontalBar';
    @Input() type: GameType = null;
    GameType = GameType;
    @Input() statistics: StatisticsModel = null;

    public chartDatasets: Array<any>;

    constructor() {
    }

    ngOnInit() {
        if (this.type === GameType.PVE) {
            this.chartDatasets = [
                {
                    data: [this.statistics.gamesPvE, this.statistics.monthGamesPvE, this.statistics.weekGamesPvE],
                    label: 'Gry'
                },
                {
                    data: [this.statistics.winGamesPvE, this.statistics.monthWinGamesPvE, this.statistics.weekWinGamesPvE],
                    label: 'Zwycięstwa'
                }
            ];
        }
        if (this.type === GameType.PVP) {
            this.chartDatasets = [
                {
                    data: [this.statistics.gamesPvP, this.statistics.monthGamesPvP, this.statistics.weekGamesPvP],
                    label: 'Gry'
                },
                {
                    data: [this.statistics.winGamesPvP, this.statistics.monthWinGamesPvP, this.statistics.weekWinGamesPvP],
                    label: 'Zwycięstwa'
                }
            ];
        }
    }

    public chartLabels: Array<any> = ['Wszystkie gry', 'Ten miesiąc', 'Ten tydzień'];

    public chartColors: Array<any> = [
        {
            backgroundColor: 'rgba(102, 194, 255, .5)',
            borderColor: 'rgba(0, 77, 128, 1)',
            borderWidth: 2,
            color: 'rgba(0, 51, 0, 1)',
        },
        {
            backgroundColor: 'rgba(133, 224, 133, .5)',
            borderColor: 'rgba(25, 102, 1)',
            borderWidth: 2,
            color: 'rgba(0, 0, 102, 1)',
        }
    ];

    public chartOptions: any = {
        responsive: true,
        legend: {
            labels: {
                fontSize: 15,
                fontColor: 'white',
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: "white",
                    fontSize: 18
                }
            }],
            xAxes: [{
                ticks: {
                    fontColor: "white",
                    fontSize: 14
                }
            }]
        }
    };

    public chartClicked(e: any): void {
    }

    public chartHovered(e: any): void {
    }
}
