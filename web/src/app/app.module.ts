import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ProfilePanelComponent } from './profile/profile-panel/profile-panel.component';
import {HttpClientModule} from "@angular/common/http";
import {ProfileService} from "./profile/profile-service/profile.service";
import {FileUploadComponent} from "./profile/file-upload/file-upload.component";
import {GameComponent} from './game/game.component';
import {BoardComponent} from './game/board/board.component';

@NgModule({
  declarations: [
    AppComponent,
    ProfilePanelComponent,
    FileUploadComponent,
    GameComponent,
    BoardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ProfileService],
  bootstrap: [AppComponent]
})
export class AppModule {}
