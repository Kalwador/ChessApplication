import {Component, OnInit} from '@angular/core';
import {RegisterModel} from '../models/register.model';
import {RegisterService} from './register-service/register.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    public registerModel: RegisterModel;
    private  facebookRegisterPath: string;

    constructor(private registerService: RegisterService) {
        this.facebookRegisterPath = this.registerService.getFacebookPath();
    }

    ngOnInit() {
        this.registerModel = new RegisterModel();
    }

    submit() {
        this.registerService.register(this.registerModel);
    }

}
