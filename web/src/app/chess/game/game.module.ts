import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './play/board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import { GameOptionsComponent } from './options/game-options.component';
import { GamePlayPveComponent } from './play/play-pve/game-play-pve.component';
import { GamePvpComponent } from './options/pvp-options/game-pvp.component';
import { GamePveComponent } from './options/pve-options/game-pve.component';
import {MaterialModule} from '../../material.module';

const routes: Routes = [
    {
        path: '',
        component: GameComponent,
        children: [
            {path: '', component: GameOptionsComponent},
            {path: 'pvp', component: GamePvpComponent},
            {path: 'pve', component: GamePveComponent},
            {path: 'play/pve/:gameId', component: GamePlayPveComponent},
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
        GamePlayPveComponent,
        GamePvpComponent,
        GamePveComponent]
})

export class GameModule {
}
