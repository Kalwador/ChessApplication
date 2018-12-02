import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {GamePvp} from "../../../../models/chess/game/game-pvp";
import {TimeType} from "../../../../models/chess/time-type.enum";
import {Subject} from "rxjs";
import {SocketMessage} from "../../../../models/socket/socket-message.model";

@Component({
    selector: 'app-game-new-pvp',
    templateUrl: './game-new-pvp.component.html',
    styleUrls: ['./game-new-pvp.component.scss'],

})
export class GameNewPvpComponent implements OnInit {

    timePerMove: TimeType = TimeType.NON;
    timeBlocks: Array<TimeType>;
    TimeType = TimeType;
    timeBlockActiveEmitter: Subject<TimeType> = new Subject<TimeType>();

    constructor(private gameService: GameService,
                private router: Router) {

        this.timeBlocks = this.transform(this.TimeType);
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

    setTimePerMove(timeType: TimeType) {
        this.timePerMove = timeType;
        this.timeBlockActiveEmitter.next(timeType);
    }

    transform(data: Object): Array<TimeType> {
        let temp = [];
        let keys =  Object.keys(data);
        for(let key of keys){
            temp.push(key);
        }
        return temp;
    }
}
