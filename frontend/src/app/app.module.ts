import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ProfilePanelComponent } from './profile-panel/profile-panel.component';
import {HttpClientModule} from "@angular/common/http";
import {ProfileService} from "./profile-service/profile.service";
import {FileUploadComponent} from "./file-upload/file-upload.component";

@NgModule({
  declarations: [
    AppComponent,
    ProfilePanelComponent,
    FileUploadComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ProfileService],
  bootstrap: [AppComponent]
})
export class AppModule {}
