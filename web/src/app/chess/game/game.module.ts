import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './play/board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {GameNewOptionsComponent} from './new-options/game-new-options.component';
import {GamePlayPveComponent} from './play/play-pve/game-play-pve.component';
import {TabViewModule} from '../../../../node_modules/primeng/primeng';
import {SharedModule} from '../../../../node_modules/primeng/shared';
import {PanelModule} from 'primeng/panel';
import {TableModule} from 'primeng/table';
import {DraggableModule} from './play/board/draggable/draggable.module';
import {PlayerHeaderComponent} from './play/player-header/player-header.component';
import {GamePlayPvpComponent} from './play/play-pvp/game-play-pvp.component';
import {MyGamesComponent} from './my-games/my-games.component';
import {MatButtonModule} from '@angular/material/button';
import {MatSliderModule} from '@angular/material/slider';
import {MatTableModule} from '@angular/material/table';
import {GamePanelComponent} from './my-games/game-panel/game-panel.component';
import {ChatComponent} from './play/chat/chat.component';
import {GameListComponent} from "./my-games/games-list/game-list.component";

const routes: Routes = [
    {
        path: '',
        component: GameComponent,
        children: [
            {path: '', component: MyGamesComponent},
            {path: 'list', component: MyGamesComponent},
            {path: 'list/:type', component: GameListComponent},
            {path: 'new-options', component: GameNewOptionsComponent},
            {path: 'play/pve/:id', component: GamePlayPveComponent},
            {path: 'play/pvp/:id', component: GamePlayPvpComponent},
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
        MatButtonModule,
        MatSliderModule,
        MatTableModule
    ],
    declarations: [
        BoardComponent,
        GameComponent,
        GameListComponent,
        GameNewOptionsComponent,
        GamePlayPveComponent,
        PlayerHeaderComponent,
        GamePlayPvpComponent,
        MyGamesComponent,
        GameListComponent,
        GameListComponent,
        GamePanelComponent,
        ChatComponent]
})

export class GameModule {
}
