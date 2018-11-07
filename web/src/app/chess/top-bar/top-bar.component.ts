import {Component, OnInit} from '@angular/core';
import {BaseService} from '../../services/base.service';

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent {

    constructor(private baseService: BaseService) {
    }

    test() {
        console.log(this.isUserLoggedIn());
    }

    reload() {
        this.baseService.reload();
    }

    public isUserLoggedIn(): boolean{
        return this.baseService.isLoggedIn();
    }

    public logout(){
        this.baseService.logOut();
    }
}
