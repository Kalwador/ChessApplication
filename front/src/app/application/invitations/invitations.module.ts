import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {InvitationsComponent} from './invitations.component';
import {RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import { InvitationsTableComponent } from './invitations-table/invitations-table.component';

const routes: Routes = [
    {
        path: '',
        component: InvitationsComponent,
        children: [
            // {path: '', component: StatisticCardComponent},
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
    ],
    declarations: [
        InvitationsComponent,
        InvitationsTableComponent,
    ]
})
export class InvitationsModule {
}
