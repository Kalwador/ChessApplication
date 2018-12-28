import {Component, OnInit} from '@angular/core';
import {AppService} from "../../services/app.service";

@Component({
    selector: 'app-statistics',
    templateUrl: './invitations.component.html',
    styleUrls: ['./invitations.component.scss']
})
export class InvitationsComponent implements OnInit {

    constructor(private appService: AppService) {
    }

    ngOnInit() {
        this.appService.get("/home").subscribe(data => {
            console.log("jest data");
            console.log(data);
        }, error => {
            console.log("omega");
            console.log(error);
        });
    }

}
