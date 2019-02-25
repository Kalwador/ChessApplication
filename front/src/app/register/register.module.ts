import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from './register.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {RegisterService} from './register-service/register.service';
import {MatButtonModule} from '@angular/material/button';
import {ActivateComponent} from "./activate/activate.component";

const routes: Routes = [
    {path: '', component: RegisterComponent},
    {path: 'activate/:username/:code', component: ActivateComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        FormsModule,
        CommonModule,
        MatButtonModule
    ],
    declarations: [
        RegisterComponent,
        ActivateComponent
    ],
    providers: [RegisterService]
})
export class RegisterModule {
}
