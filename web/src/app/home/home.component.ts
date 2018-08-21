import {Component, OnInit} from '@angular/core';
import {HomeService} from './home-service/home.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    message: string;

    constructor(private homeService: HomeService) {
    }

    ngOnInit() {
        this.homeService.getGreeting().subscribe(data => {
            this.message = data;
        });
        console.log(this.message);
    }
}
