import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {GamePvp} from "../../../../models/chess/game/game-pvp";

@Component({
    selector: 'app-game-pvp',
    templateUrl: './game-pvp.component.html',
    styleUrls: ['./game-pvp.component.scss']
})
export class GamePvpComponent implements OnInit {

    constructor(private gameService: GameService,
                private router: Router) {
    }

    ngOnInit() {
    }

    submit() {
        let game = new GamePvp();
        // game.
        this.gameService.newPvP(game).subscribe(value => {
            this.router.navigate(['/game/play/pvp', value]);
        });
    }
}
