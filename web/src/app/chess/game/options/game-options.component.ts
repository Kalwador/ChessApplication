import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-game-options',
    templateUrl: './game-options.component.html',
    styleUrls: ['./game-options.component.css']
})
export class GameOptionsComponent implements OnInit {

    constructor(private gameService: GameService,
                private router: Router) {

    }

    ngOnInit() {

    }

}
