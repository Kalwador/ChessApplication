import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {ChessComponent} from './chess.component';
import {TopBarComponent} from './top-bar/top-bar.component';
import {LoginModalComponent} from './top-bar/login/login-modal.component';
import {FooterComponent} from './footer/footer.component';

const routes: Routes = [
    {
        path: '',
        component: ChessComponent,
        children: [
            {path: '', loadChildren: './home/home.module#HomeModule'},
            {path: 'game', loadChildren: './game/game.module#GameModule'},
            {path: 'profile', loadChildren: './profile/profile.module#ProfileModule'}
        ]
    }
];

@NgModule({
    imports: [
        HttpModule,
        FormsModule,
        HttpClientModule,
        NgbModule.forRoot(),
        RouterModule.forChild(routes)
    ],
    declarations: [
        ChessComponent,
        TopBarComponent,
        LoginModalComponent,
        FooterComponent
    ],
    providers: [],
    bootstrap: [ChessComponent]
})
export class ChessModule {
}
