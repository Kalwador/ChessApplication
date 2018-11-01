import {Component, OnInit} from '@angular/core';
import {GameService} from '../../service/game.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-game-pve',
    templateUrl: './game-pve.component.html',
    styleUrls: ['./game-pve.component.css']
})
export class GamePveComponent implements OnInit {

    colors: Array<string>;
    choosedColor: 'RANDOM';
    level: number = 3;

    constructor(private gameService: GameService,
                private router: Router) {

        this.colors = ['WHITE', 'BLACK', 'RANDOM'];
    }

    ngOnInit() {
        this.choosedColor = 'RANDOM';
    }

    submit() {
        this.gameService.newPvE(this.choosedColor, this.level).subscribe(value => {
            this.router.navigate(['/game/play/pve', value]);
        });
    }

}
