import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StatisticsComponent} from './statistics.component';
import {RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {StatisticCardComponent} from './statistic-card/statistic-card.component';
import {ChartsModule, WavesModule, TableModule} from 'angular-bootstrap-md';
import {StatisticTableComponent} from './statistic-card/statistic-table/statistic-table.component';
import {StatisticChartComponent} from './statistic-card/statistic-chart/statistic-chart.component'
import {StatisticMiniChartComponent} from "./statistic-card/statistic-mini-charts/statistic-mini-chart.component";

const routes: Routes = [
    {
        path: '',
        component: StatisticsComponent,
        children: [
            {path: '', component: StatisticCardComponent},
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ChartsModule,
        TableModule,
        WavesModule,
    ],
    declarations: [
        StatisticsComponent,
        StatisticCardComponent,
        StatisticTableComponent,
        StatisticChartComponent,
        StatisticMiniChartComponent
    ]
})
export class StatisticsModule {
}
