import {Component, OnInit} from '@angular/core';
import {BaseService} from '../../services/base.service';

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

    constructor(private baseService: BaseService) {
    }

    ngOnInit() {
    }

    test() {
        console.log(this.baseService.isLogedIn());
    }
}
