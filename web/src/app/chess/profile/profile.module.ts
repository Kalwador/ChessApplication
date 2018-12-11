import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from './profile.component';
import {ProfileFormComponent} from './profile-form/profile-form.component';
import {ProfileViewComponent} from './profile-view/profile-view.component';
import {FormsModule} from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import {AvatarPanelComponent} from "./avatar-panel/avatar-panel.component";
const routes: Routes = [
    {
        path: '',
        component: ProfileComponent,
        children: [
            {path: '', component: ProfileFormComponent},
            {path: 'view/:nick', component: ProfileViewComponent},
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        MatRadioModule
    ],
    declarations: [
        ProfileComponent,
        ProfileFormComponent,
        ProfileViewComponent,
        AvatarPanelComponent
    ]
})
export class ProfileModule {
}
