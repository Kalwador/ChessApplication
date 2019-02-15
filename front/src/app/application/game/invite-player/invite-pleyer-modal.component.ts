import {Component, EventEmitter, Input, Output} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {OauthService} from '../../../services/oauth.service';
import {ProfileService} from '../../profile/profile-service/profile.service';
import {Router} from "@angular/router";
import {NotificationService} from "../../notifications/notification.service";
import {AppService} from "../../../services/app.service";
import {TimeType} from "../../../models/chess/time-type.enum";
import {setOffsetToUTC} from "ngx-bootstrap/chronos/units/offset";
import {InvitationsService} from "../service/invitations.service";

@Component({
    selector: 'app-invite-player-modal',
    templateUrl: './invite-pleyer-modal.component.html',
    styleUrls: ['./invite-pleyer-modal.component.scss']
})
export class InvitePlayerModalComponent {
    closeResult: string;
    modalReference: any;
    errorMessage: string = null;

    nick: string;
    @Input() type: string;
    @Output() emiter: EventEmitter<string> = new EventEmitter();

    constructor(private ngbModal: NgbModal,
                private invitationService: InvitationsService,
                private profileService: ProfileService,
                private appService: AppService,
                private router: Router,
                private notificationService: NotificationService) {
    }

    open(content) {
        this.modalReference = this.ngbModal.open(content, {ariaLabelledBy: 'modal-basic-title'});
        this.modalReference.result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

    private sumbit() {
        if(this.appService.accountModel.nick == this.nick){
            this.errorMessage = "Nie można grać ze sobą!";
            return;
        }
        this.invitationService.checkNick(this.nick).subscribe(data => {
            if (data == "true"){
                this.emiter.emit(this.nick);
                this.modalReference.dismiss();
            } else {
                this.errorMessage = "Gracz o podanym nicku nie istnieje"
            }
        }, error1 => {
            this.errorMessage = "Gracz o podanym nicku nie istnieje";
            console.log(error1.status);
        })
    }
}