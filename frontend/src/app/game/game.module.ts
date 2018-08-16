import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoardComponent } from './board/board.component';
import { GameComponent } from './game.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [BoardComponent, GameComponent]
})
export class GameModule { }
