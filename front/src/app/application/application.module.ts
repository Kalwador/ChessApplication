import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {ApplicationComponent} from './application.component';
import {TopBarComponent} from './top-bar/top-bar.component';
import {LoginModalComponent} from './top-bar/login/login-modal.component';
import {FooterComponent} from './footer/footer.component';
import {CommonModule} from '@angular/common';

const routes: Routes = [
    {
        path: '',
        component: ApplicationComponent,
        children: [
            {path: '', loadChildren: './home/home.module#HomeModule'},
            {path: 'game', loadChildren: './game/game.module#GameModule'},
            {path: 'game:type', loadChildren: './game/game.module#GameModule'},
            {path: 'statistics', loadChildren: './statistics/statistics.module#StatisticsModule'},
            {path: 'invitations', loadChildren: './invitations/invitations.module#InvitationsModule'},
            {path: 'profile', loadChildren: './profile/profile.module#ProfileModule'}
        ]
    }
];

@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        FormsModule,
        HttpClientModule,
        NgbModule.forRoot(),
        RouterModule.forChild(routes)
    ],
    declarations: [
        ApplicationComponent,
        TopBarComponent,
        LoginModalComponent,
        FooterComponent
    ],
    providers: [],
    bootstrap: [ApplicationComponent]
})
export class ApplicationModule {
}
