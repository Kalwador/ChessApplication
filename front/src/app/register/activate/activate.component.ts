import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RegisterService} from "../register-service/register.service";
import {NotificationService} from "../../application/notifications/notification.service";

@Component({
    selector: 'app-register-activate',
    templateUrl: './activate.component.html',
    styleUrls: ['./activate.component.scss']
})
export class ActivateComponent {
    status: boolean = true;
    message: string = '';
    constructor(private route: ActivatedRoute,
                private router: Router,
                private registerService: RegisterService,
                private notificationService: NotificationService) {

        this.route.params.subscribe(params => {
            this.registerService.activate(params['username'], params['code']).subscribe(data => {
            }, error => {
                if (error.status === 400 || error.status === 404) {
                    this.status = false;
                    this.message = 'Konto już zostało odblokowane!';
                }
                if (error.status === 409) {
                    this.status = false;
                    this.message = 'Błędy kod aktywacyjny lub konto nie istnieje!!';
                }
            })
        });


    }
}
