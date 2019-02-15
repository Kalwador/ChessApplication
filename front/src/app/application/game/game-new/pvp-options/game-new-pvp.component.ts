import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {GamePvpModel} from "../../../../models/chess/game/game-pvp-model";
import {TimeType} from "../../../../models/chess/time-type.enum";
import {Subject} from "rxjs";
import {SocketMessageModel} from "../../../../models/socket/socket-message.model";
import {InvitationsService} from "../../service/invitations.service";

@Component({
    selector: 'app-game-new-pvp',
    templateUrl: './game-new-pvp.component.html',
    styleUrls: ['./game-new-pvp.component.scss'],

})
export class GameNewPvpComponent {

    timePerMove: TimeType = TimeType.NON;
    timeBlocks: Array<TimeType>;
    TimeType = TimeType;
    timeBlockActiveEmitter: Subject<TimeType> = new Subject<TimeType>();

    constructor(private gameService: GameService,
                private invitationService: InvitationsService,
                private router: Router) {

        this.timeBlocks = this.transform(this.TimeType);
    }

    findGame() {
        let game = new GamePvpModel();
        this.gameService.findPvP(game).subscribe(value => {
            this.router.navigate(['/game/play/pvp', value]);
        });
    }

    sendInvitation(nick: string){
        let game = new GamePvpModel();
        this.gameService.newPvP(game).subscribe(value => {
            this.invitationService.sendInvitation(value, nick);
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
