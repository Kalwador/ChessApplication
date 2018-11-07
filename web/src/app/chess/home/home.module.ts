import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {RouterModule, Routes} from '@angular/router';
import {HomeService} from './home-service/home.service';
import {FormsModule} from '@angular/forms';
import { ArticleComponent } from './article/article.component';


const routes: Routes = [
    {path: '', component: HomeComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
    ],
    declarations: [
        HomeComponent,
        ArticleComponent
    ],
    providers: [HomeService]
})
export class HomeModule {
}
