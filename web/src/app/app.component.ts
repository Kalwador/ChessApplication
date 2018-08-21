import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <app-top-bar></app-top-bar>
        <router-outlet></router-outlet>
        <app-footer></app-footer>`
})
export class AppComponent {
}
