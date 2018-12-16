import {Component, OnInit} from '@angular/core';
import {AppService} from "../../../services/app.service";

@Component({
    selector: 'app-invitations-table',
    templateUrl: './invitations-table.component.html',
    styleUrls: ['./invitations-table.component.scss']
})
export class InvitationsTableComponent implements OnInit {

    constructor(private appService: AppService) {
    }

    ngOnInit() {
    }

}
