import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './board/board.component';
import {GameComponent} from './game.component';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
    {path: '', component: GameComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule
    ],
    declarations: [BoardComponent, GameComponent]
})
export class GameModule {
}
