import {Component, Input, OnInit} from '@angular/core';
import {AppService} from "../../../services/app.service";
import {GameType} from "../../../models/chess/game/game-type.enum";
import {GamePvpModel} from "../../../models/chess/game/game-pvp-model";
import {GamePveModel} from "../../../models/chess/game/game-pve-model";
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../../game/service/game.service";
import {NotificationService} from "../../notifications/notification.service";
import {ProfileService} from "../../profile/profile-service/profile.service";
import {InvitationModel} from "../../../models/invitations/invitation.model";
import {InvitationsService} from "../../game/service/invitations.service";

@Component({
    selector: 'app-invitations-table',
    templateUrl: './invitations-table.component.html',
    styleUrls: ['./invitations-table.component.scss']
})
export class InvitationsTableComponent implements OnInit {

    @Input() listSize: number = 7;
    invitations: Array<InvitationModel> = new Array();
    currentPvPPage: number = 0;

    constructor(private router: Router,
                private service: InvitationsService,
                private notif: NotificationService) {
    }

    ngOnInit() {
        this.loadInvitations(this.currentPvPPage);
    }

    loadInvitations(page: number) {
        this.service.getInvitations(page, this.listSize).subscribe(data => {
            this.invitations = data.content;
            console.log(data.totalElements);
            if (data.totalElements > 0) {
                console.log("Pusto");
            }
        }, error => {
            this.notif.trace(error);
        });
    }

}
