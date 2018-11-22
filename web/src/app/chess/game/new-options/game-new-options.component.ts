import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-game-options',
    templateUrl: './game-new-options.component.html',
    styleUrls: ['./game-new-options.component.css']
})
export class GameNewOptionsComponent implements OnInit {

    constructor(private gameService: GameService,
                private router: Router) {
    }

    ngOnInit() {}

}
