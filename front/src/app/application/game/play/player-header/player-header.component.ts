import {Component, Input, OnInit} from '@angular/core';
import {PlayerColor} from "../../../../models/chess/player-color.enum";
import {InvitationsService} from "../../service/invitations.service";

@Component({
    selector: 'player-header',
    templateUrl: './player-header.component.html',
    styleUrls: ['./player-header.component.scss']
})
export class PlayerHeaderComponent implements OnInit{

    @Input() data: string;
    @Input() color: PlayerColor;
    PlayerColor = PlayerColor;
    @Input() gameId: number;
    @Input() whiteStatus: string = "";
    @Input() blackStatus: string = "";

    constructor(private invitationService: InvitationsService) {
        console.log("Ypooooo");2
    }

    sendInvitation(nick: string) {
        console.log(3);
        this.invitationService.sendInvitation(this.gameId, nick);
    }

    ngOnInit(): void {
        console.log(this.data);
    }
}
