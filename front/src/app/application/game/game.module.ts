import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './play/board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {GameNewComponent} from './game-new/game-new.component';
import {GamePlayPveComponent} from './play/play-pve/game-play-pve.component';
import {TabViewModule} from '../../../../node_modules/primeng/primeng';
import {SharedModule} from '../../../../node_modules/primeng/shared';
import {PanelModule} from 'primeng/panel';
import {TableModule} from 'primeng/table';
import {DraggableModule} from './play/board/draggable/draggable.module';
import {PlayerHeaderComponent} from './play/player-header/player-header.component';
import {GamePlayPvpComponent} from './play/play-pvp/game-play-pvp.component';
import {GameListComponent} from './game-list/game-list.component';
import {MatButtonModule} from '@angular/material/button';
import {MatSliderModule} from '@angular/material/slider';
import {MatTableModule} from '@angular/material/table';
import {GamePanelComponent} from './game-list/game-panel/game-panel.component';
import {ChatComponent} from './play/chat/chat.component';
import {GameListTableComponent} from "./game-list/games-list-table/game-list-table.component";
import {GameNewPvpComponent} from './game-new/pvp-options/game-new-pvp.component';
import {GameNewPveComponent} from './game-new/pve-options/game-new-pve.component';
import { TimeBlockComponent } from './game-new/pvp-options/time-block/time-block.component';
import {MatCardModule} from "@angular/material";
import {InvitePlayerModalComponent} from "./invite-player/invite-pleyer-modal.component";

const routes: Routes = [
    {
        path: '',
        component: GameComponent,
        children: [
            {path: '', component: GameListComponent},
            {path: 'list', component: GameListComponent},
            {path: 'list/:type', component: GameListTableComponent},
            {path: 'new-options', component: GameNewComponent},
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
        MatTableModule,
        MatCardModule,
    ],
    declarations: [
        GameComponent,
        GameListComponent,
        GameListTableComponent,
        GameNewComponent,
        GameNewPveComponent,
        GameNewPvpComponent,
        GamePlayPveComponent,
        GamePlayPvpComponent,
        PlayerHeaderComponent,
        GamePanelComponent,
        InvitePlayerModalComponent,
        ChatComponent,
        BoardComponent,
        TimeBlockComponent
    ],
    exports: [
    ]
})

export class GameModule {
}
