import {Component} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {OauthService} from '../../../services/oauth.service';
import {ProfileService} from '../../profile/profile-service/profile.service';
import {Router} from "@angular/router";
import {NotificationService} from "../../notifications/notification.service";
import {AppService} from "../../../services/app.service";

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
                private router: Router,
                private oauthService: OauthService,
                private baseService: AppService,
                private profileService: ProfileService,
                private notificationService: NotificationService) {
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
        this.oauthService.login(this.username, this.password).subscribe(data => {

            //TOKEN
            this.oauthService.token = data;
            this.oauthService.putTokenToStorage(data);
            this.router.navigate(['/']);

            //LOGEDIN
            this.baseService.isUserLoggedIn = true;
            this.notificationService.trace('Poprawnie zalogowano');

            this.baseService.getUserProfile();
            this.modalReference.dismiss();
        }, error => {
            switch (error.status) {
                case (400): {
                    this.notificationService.warning('Błędny login i/lub hasło');
                    //TODO - wyswietl info ze zle dane
                    return false;
                }
                case (401): {
                    this.router.navigate(['/']);
                    this.notificationService.danger('Brak autoryzacji dla aplikacji');
                    //TODO - wyswietl info ze brak autoryzacji dla serwisu
                }
                default : {
                    this.router.navigate(['/']);
                    this.notificationService.danger('Nieznany błąd!');
                }
            }
        });


        //
    }
}