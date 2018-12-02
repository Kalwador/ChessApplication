import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-game-new',
    templateUrl: './game-new.component.html',
    styleUrls: ['./game-new.component.css']
})
export class GameNewComponent implements OnInit {

    constructor(private gameService: GameService,
                private router: Router) {
    }

    ngOnInit() {}

}
