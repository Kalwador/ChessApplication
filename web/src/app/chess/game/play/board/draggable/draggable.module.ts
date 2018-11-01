import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {DroppableService} from './droppable.service';
import {DroppableDirective} from './droppable.directive';
import {DropzoneDirective} from './dropzone.directive';
import {OverlayModule} from '@angular/cdk/overlay';
import {DraggableHelperDirective} from './draggable-helper.directive';
import {MovableAreaDirective} from './moveable-area.directive';
import {DraggableDirective} from './draggable.directive';
import {MovableDirective} from './moveable.directive';

@NgModule({
  imports: [CommonModule, OverlayModule],
  declarations: [
    DraggableDirective,
    MovableDirective,
    MovableAreaDirective,
    DraggableHelperDirective,
    DropzoneDirective,
    DroppableDirective
  ],
  exports: [
    DraggableDirective,
    MovableDirective,
    MovableAreaDirective,
    DraggableHelperDirective,
    DropzoneDirective,
    DroppableDirective
  ],
  providers: [
    DroppableService
  ]
})
export class DraggableModule {}
