import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from './profile.component';
import {ProfileFormComponent} from './profile-form/profile-form.component';
import {ProfileTableComponent} from './profile-table/profile-table.component';
import {FormsModule} from '@angular/forms';

const routes: Routes = [
    {
        path: '',
        component: ProfileComponent,
        children: [
            {path: '', component: ProfileTableComponent},
            {path: 'edit', component: ProfileFormComponent},
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
        ProfileComponent,
        ProfileFormComponent,
        ProfileTableComponent]
})
export class ProfileModule {
}
