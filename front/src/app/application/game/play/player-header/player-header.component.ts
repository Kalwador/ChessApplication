import {Component, Input, OnInit} from '@angular/core';
import {PlayerColor} from "../../../../models/chess/player-color.enum";
import {InvitationsService} from "../../service/invitations.service";
import {NotificationService} from "../../../notifications/notification.service";

@Component({
    selector: 'player-header',
    templateUrl: './player-header.component.html',
    styleUrls: ['./player-header.component.scss']
})
export class PlayerHeaderComponent implements OnInit {

    @Input() data: string;
    @Input() color: PlayerColor;
    PlayerColor = PlayerColor;
    @Input() gameId: number;
    @Input() whiteStatus: string = "";
    @Input() blackStatus: string = "";

    constructor(private invitationService: InvitationsService,
                private notificationService: NotificationService) {
    }

    sendInvitation(nick: string) {
        this.invitationService.sendInvitation(this.gameId, nick).subscribe(data => {
            this.notificationService.info("Gracz " + nick + " został zaproszony do rozgrywki!");
        });
    }

    ngOnInit(): void {
    }
}
