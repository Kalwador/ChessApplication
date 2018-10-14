import {Component, OnInit} from '@angular/core';
import {HomeService} from './home-service/home.service';
import {PieceTypeEnum} from '../../models/game/piece-type.enum';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    test: PieceTypeEnum;
    message: string;

    constructor(private homeService: HomeService) {
        if(this.homeService.isLogedIn()){
            console.log('zalogowany!!!');
        }
        this.test = PieceTypeEnum.KING;
    }

    ngOnInit() {
        this.homeService.getGreeting().subscribe(data => {
            this.message = data;
            console.log(this.message);
        });
    }
}
