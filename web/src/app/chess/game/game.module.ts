import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './play/board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import { GameNewOptionsComponent } from './new-options/game-new-options.component';
import { GamePlayPveComponent } from './play/play-pve/game-play-pve.component';
import { GamePvpComponent } from './new-options/pvp-options/game-pvp.component';
import { GamePveComponent } from './new-options/pve-options/game-pve.component';
import {TabViewModule} from '../../../../node_modules/primeng/primeng';
import {SharedModule} from '../../../../node_modules/primeng/shared';
import {PanelModule} from 'primeng/panel';
import {TableModule} from 'primeng/table';
import {DraggableModule} from './play/board/draggable/draggable.module';
import { PlayerHeaderComponent } from './play/player-header/player-header.component';
import { GamePlayPvpComponent } from './play/play-pvp/game-play-pvp.component';
import { GameListComponent } from './games/game-list.component';
import { GamePvpListComponent } from './games/games-pvp/game-pvp-list.component';
import { GamePveListComponent } from './games/games-pve/game-pve-list.component';
import {MatButtonModule} from '@angular/material/button';

const routes: Routes = [
    {
        path: '',
        component: GameComponent,
        children: [
            {path: '', component: GameListComponent},
            {path: 'list', component: GameListComponent},
            {path: 'list-pve', component: GamePveListComponent},
            {path: 'list-pvp', component: GamePvpListComponent},
            {path: 'new-options', component: GameNewOptionsComponent},
            {path: 'play/pve/:gameId', component: GamePlayPveComponent},
            {path: 'play/pvp/:gameId', component: GamePlayPvpComponent},
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        TabViewModule,
        SharedModule,
        PanelModule,
        TableModule,
        DraggableModule,
        MatButtonModule
    ],
    declarations: [
        BoardComponent,
        GameComponent,
        GameNewOptionsComponent,
        GamePlayPveComponent,
        GamePvpComponent,
        GamePveComponent,
        PlayerHeaderComponent,
        GamePlayPvpComponent,
        GameListComponent,
        GamePvpListComponent,
        GamePveListComponent]
})

export class GameModule {
}
