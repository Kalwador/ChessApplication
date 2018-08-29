import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './game-play/board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import { GameOptionsComponent } from './game-options/game-options.component';
import { GamePlayComponent } from './game-play/game-play.component';
import { GamePvpComponent } from './game-pvp/game-pvp.component';
import { GamePveComponent } from './game-pve/game-pve.component';
import {SliderConfigurableExample} from './game-pve/slider-configurable-example/slider-configurable-example.component';
import {MaterialModule} from '../../material.module';

const routes: Routes = [
    {
        path: '',
        component: GameComponent,
        children: [
            {path: '', component: GameOptionsComponent},
            {path: 'pvp', component: GamePvpComponent},
            {path: 'pve', component: GamePveComponent},
            {path: 'play', component: GamePlayComponent},
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        MaterialModule,
        CommonModule,
        FormsModule,
    ],
    declarations: [
        BoardComponent,
        GameComponent,
        GameOptionsComponent,
        GamePlayComponent,
        GamePvpComponent,
        GamePveComponent,
        SliderConfigurableExample]
})

export class GameModule {
}
