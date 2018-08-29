import {Component, OnInit} from '@angular/core';
import {GameService} from '../game-service/game.service';

@Component({
    selector: 'app-game-pve',
    templateUrl: './game-pve.component.html',
    styleUrls: ['./game-pve.component.css']
})
export class GamePveComponent implements OnInit {

    color: number = 3;
    level: number = 3;

    constructor(private gameService: GameService) {
    }

    ngOnInit() {
    }

    submit(){
        this.gameService.newPvE(this.color, this.level);
    }

}
