import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {InvitationModel} from "../../../models/invitations/invitation.model";
import {InvitationsService} from "../../game/service/invitations.service";
import {ProfileService} from "../../profile/profile-service/profile.service";
import {NotificationService} from "../../notifications/notification.service";


@Component({
    selector: 'app-inv-list-panel',
    templateUrl: './invitation-panel.component.html',
    styleUrls: ['./invitation-panel.component.scss']
})
export class InvitationPanelComponent implements OnInit {

    @Input() invitation: InvitationModel;

    constructor(
        private invitationService: InvitationsService,
        private notificationService: NotificationService,
        private router: Router) {
    }

    ngOnInit() {
        console.log(this.invitation);
    }

    accept() {
        this.invitationService.accept(this.invitation.game.id).subscribe(data => {
            this.router.navigate(['/game/play/pvp', this.invitation.game.id]);
        });
    }

    decline() {
        this.invitationService.decline(this.invitation.game.id).subscribe(data => {
            this.notificationService.info("Gra porzucona");
        });
    }
}
