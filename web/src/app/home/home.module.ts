import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {RouterModule, Routes} from '@angular/router';
import {HomeService} from './home-service/home.service';


const routes: Routes = [
    {path: '', component: HomeComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule
    ],
    declarations: [HomeComponent],
    providers: [HomeService]
})
export class HomeModule {
}
