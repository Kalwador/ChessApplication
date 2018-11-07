import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSnackBarModule} from "@angular/material";
import {NotificationComponent} from "./chess/notifications/notification.service";

const routes: Routes = [
    {path: '', loadChildren: './chess/chess.module#ChessModule'},
    {path: 'register', loadChildren: './register/register.module#RegisterModule'}
];

@NgModule({
    imports: [
        BrowserAnimationsModule,
        BrowserModule,
        HttpModule,
        FormsModule,
        HttpClientModule,
        NgbModule.forRoot(),
        RouterModule.forRoot(routes),
        MatSnackBarModule
    ],
    declarations: [
        AppComponent,
        NotificationComponent
    ],
    providers: [],
    bootstrap: [AppComponent],
    entryComponents: [
        NotificationComponent,
    ],
    exports: [ NotificationComponent ]
})
export class AppModule {
}



