import {Component, OnInit} from '@angular/core';
import {RegisterModel} from '../models/register.model';
import {RegisterService} from './register-service/register.service';
import {Router} from "@angular/router";
import {NotificationService} from "../application/notifications/notification.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    public registerModel: RegisterModel;
    private facebookRegisterPath: string;
    password2: string = '';
    errorMessage: string = null;

    constructor(private router: Router,
                private registerService: RegisterService,
                private notificationService: NotificationService) {
        this.facebookRegisterPath = this.registerService.getFacebookPath();
    }

    ngOnInit() {
        this.registerModel = new RegisterModel();
        this.registerModel.email = '';
        this.registerModel.username = '';
        this.registerModel.password = '';
    }

    submit() {
        if (this.validate(this.registerModel, this.password2)) {
            this.registerService.register(this.registerModel).subscribe(() => {
                    this.notificationService.info('Pomyslnie zarejestrowano');
                    this.router.navigate(['/']);
                },
                error1 => {
                    this.errorMessage = error1._body;
                })
        }
    }

    validate(registerModel: RegisterModel, password2: string): boolean {
        if (registerModel.username.trim() === '') {
            this.errorMessage = "Wprowadź nazwę użytkownika!";
            return false;
        }
        if (registerModel.username.length < 4) {
            this.errorMessage = "Username jest za krótki!";
            return false;
        }
        if (registerModel.username.length > 25) {
            this.errorMessage = "Username jest za długi!";
            return false;
        }
        if (registerModel.email.trim() === '') {
            this.errorMessage = "Wprowadź adres e-mail!";
            return false;
        }
        if (registerModel.email.length < 4) {
            this.errorMessage = "Podany e-mail jest nieprawidłowy!";
            return false;
        }
        //TODO validate email adres
        if (registerModel.password.trim() === '') {
            this.errorMessage = "Wprowadź hasło!";
            return false;
        }
        if (registerModel.password.length < 8) {
            this.errorMessage = "Hasło jest za krótkie!";
            return false;
        }
        if (registerModel.password.length > 25) {
            this.errorMessage = "Hasło jest za długie!";
            return false;
        }
        if (this.password2.trim() === '') {
            this.errorMessage = "Wprowadź powtórzenie hasła!";
            return false;
        }
        if (registerModel.password !== password2) {
            this.errorMessage = "Hasła nie są zgodne!";
            return false;
        }
        return true;
    }
}
