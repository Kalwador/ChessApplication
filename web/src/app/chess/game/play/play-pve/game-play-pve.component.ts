import {Component, OnInit} from '@angular/core';
import {GamePve} from '../../../../models/game/game-pve';
import {ActivatedRoute} from '@angular/router';
import {GameService} from '../../service/game.service';
import {Field} from '../../../../models/game/field.model';

@Component({
    selector: 'app-game-play',
    templateUrl: './game-play-pve.component.html',
    styleUrls: ['./game-play-pve.component.css']
})
export class GamePlayPveComponent implements OnInit {

    game: GamePve;
    fields: Array<Array<Field>>;

    constructor(private route: ActivatedRoute,
                private gameService: GameService) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.gameService.getPVEGame(+params['gameId']).subscribe(data => {
                this.game = data;
                this.fields = this.gameService.createBoard(this.game.board);
            });
        });
    }

}
