import {Component} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {OauthService} from '../../../services/oauth.service';
import {ProfileService} from '../../profile/profile-service/profile.service';

@Component({
    selector: 'app-login-modal',
    templateUrl: './login-modal.component.html',
    styleUrls: ['./login-modal.component.css']
})
export class LoginModalComponent {
    closeResult: string;
    username: string;
    password: string;

    modalReference: any;

    constructor(private ngbModal: NgbModal,
                private oauthService: OauthService,
                private profileService: ProfileService) {
    }

    open(content) {
        this.oauthService.getCredentials();
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
        if (this.oauthService.login(this.username, this.password)) {
            this.modalReference.dismiss();
        }
    }
}