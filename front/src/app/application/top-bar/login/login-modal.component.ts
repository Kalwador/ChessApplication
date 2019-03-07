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
    styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {
    closeResult: string;
    username: string = '';
    password: string = '';

    modalReference: any;
    errorMessage: string = '';

    isUnlockAccountMode: boolean = false; //TODO inny sposob odblokowania konta

    constructor(private ngbModal: NgbModal,
                private router: Router,
                private oauthService: OauthService,
                private appService: AppService,
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
        if(this.validate()){
            this.oauthService.login(this.username, this.password).subscribe(data => {

                //TOKEN
                this.oauthService.token = data;
                this.oauthService.putTokenToStorage(data);
                this.router.navigate(['/']);

                //LOGED IN
                this.appService.isUserLoggedIn = true;
                this.notificationService.trace('Poprawnie zalogowano');
                this.appService.getUserProfile();

                this.modalReference.dismiss();
            }, error => {
                switch (error.status) {
                    case (400): {
                        if(JSON.parse(error._body).error_description === 'Bad credentials'){
                            this.notificationService.warning('Błędny login i/lub hasło');
                            this.errorMessage = 'Błędny login i/lub hasło';
                        }

                        if(JSON.parse(error._body).error_description === 'User account is locked'){
                            this.notificationService.warning('Konto nie zostało odblokowane. Kod aktywacyjny został wysłany na adres email');
                            this.errorMessage = 'Konto nie zostało odblokowane. Kod aktywacyjny został wysłany na adres email';
                        }
                        return false;
                    }
                    case (401): {
                        this.router.navigate(['/']);
                        this.notificationService.danger('Brak autoryzacji dla aplikacji');
                        //TODO - wyswietl info ze brak autoryzacji dla serwisu
                        return false;
                    }
                    default : {
                        this.router.navigate(['/']);
                        console.log(JSON.parse(error._body).error_description);
                        this.notificationService.danger('Nieznany błąd!');
                    }
                }
            });
        }
    }

    private validate(): boolean{
        if(this.username.trim() === ''){
            this.errorMessage = 'Podaj username lub email';
            return false;
        }
        if(this.password.trim() === ''){
            this.errorMessage = 'Podaj hasło';
            return false;
        }
        return true;
    }
}