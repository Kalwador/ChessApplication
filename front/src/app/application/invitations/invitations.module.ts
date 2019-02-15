import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {InvitationsComponent} from './invitations.component';
import {RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import { InvitationsTableComponent } from './invitations-table/invitations-table.component';
import {InvitationPanelComponent} from "./invitation-panel/invitation-panel.component";

const routes: Routes = [
    {
        path: '',
        component: InvitationsComponent,
        children: [
            {path: '', component: InvitationsTableComponent},
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
        InvitationPanelComponent
    ]
})
export class InvitationsModule {
}
