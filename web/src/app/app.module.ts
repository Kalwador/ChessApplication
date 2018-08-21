import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {TopBarComponent} from './top-bar/top-bar.component';
import {FooterComponent} from './footer/footer.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpModule} from '@angular/http';

const routes: Routes = [
    { path: '', loadChildren: './home/home.module#HomeModule' },
    { path: 'game', loadChildren: './game/game.module#GameModule' },
    { path: 'profile', loadChildren: './profile/profile.module#ProfileModule' },
    { path: 'login', loadChildren: './login/login.module#LoginModule' },
    { path: 'register', loadChildren: './register/register.module#RegisterModule' }
];

@NgModule({
    declarations: [
        AppComponent,
        TopBarComponent,
        FooterComponent
    ],
    imports: [
        BrowserModule,
        HttpModule,
        HttpClientModule,
        NgbModule.forRoot(),
        RouterModule.forRoot(routes)
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
