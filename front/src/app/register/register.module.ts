import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from './register.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {RegisterService} from './register-service/register.service';
import {MatButtonModule} from '@angular/material/button';

const routes: Routes = [
    {path: '', component: RegisterComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        FormsModule,
        CommonModule,
        MatButtonModule
    ],
    declarations: [RegisterComponent],
    providers: [RegisterService]
})
export class RegisterModule {
}
