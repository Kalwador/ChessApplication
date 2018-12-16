import {Component, OnInit} from '@angular/core';
import {AccountModel} from "../../../models/profile/account.model";
import {GenderEnum} from "../../../models/profile/gender.enum";
import {AppService} from "../../../services/app.service";
import {ProfileService} from "../profile-service/profile.service";
import {RegisterModel} from "../../../models/register.model";
import {NotificationService} from "../../notifications/notification.service";

@Component({
    selector: 'app-profile-form',
    templateUrl: './profile-form.component.html',
    styleUrls: ['./profile-form.component.scss']
})
export class ProfileFormComponent implements OnInit {

    isInfoTabActive: boolean = true;
    profile: AccountModel;
    password1: string;
    password2: string;
    gender: string;
    genders = ["Kobieta", "Mężczyzna"];
    errorMessage: string = null;

    constructor(public appService: AppService,
                private profileService: ProfileService,
                private notificationService: NotificationService) {
        this.profile = appService.accountModel;

    }

    ngOnInit() {
        if (!this.appService.isLoggedIn()) {
            this.appService.logOut();
        }
        this.setGender();
    }

    changeTab(isActive: boolean) {
        this.isInfoTabActive = isActive;
    }

    saveInfo() {
        this.profile.gender = this.parseGender(this.gender);
        this.profileService.updateInfo(this.profile).subscribe(() => {
            this.notificationService.info("Zaktualizowano profil");
        });
    }

    saveDetails() {
        let details = new RegisterModel();
        details.username = this.profile.username;
        details.email = this.profile.email;
        details.password = this.password1;

        if (this.validateDetails(details, this.password2)) {
            this.profileService.updateDetails(details).subscribe(data => {
                console.log(data);
            }, error => {
                console.log(error);
            });
        }
    }

    setGender() {
        switch (this.profile.gender) {
            case GenderEnum.MALE: {
                this.gender = "Mężczyzna";
                break;
            }
            case GenderEnum.FEMALE: {
                this.gender = "Kobieta";
                break;
            }
        }
    }

    parseGender(gender: string): GenderEnum {
        switch (gender) {
            case "Mężczyzna": {
                return GenderEnum.MALE;
            }
            case "Kobieta": {
                return GenderEnum.FEMALE;
            }
        }
    }

    validateDetails(registerModel: RegisterModel, password2: string): boolean {
        if (registerModel.username.length < 4) {
            this.errorMessage = "Username jest za krótki!";
            return false;
        }
        if (registerModel.username.length > 25) {
            this.errorMessage = "Username jest za długi!";
            return false;
        }
        if (registerModel.password !== null && registerModel.password !== undefined) {
            if (registerModel.password.length < 8) {
                this.errorMessage = "Hasło jest za krótkie!";
                return false;
            }
            if (registerModel.password.length > 25) {
                this.errorMessage = "Hasło jest za długie!";
                return false;
            }
            if (registerModel.password !== password2) {
                this.errorMessage = "Hasła nie są zgodne!";
                return false;
            }
        }
        if (registerModel.email.length < 4) {
            this.errorMessage = "Podany email jest nieprawidłowy!";
            return false;
        }
        return true;
    }
}
