import {Component} from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {OauthService} from '../../../services/oauth.service';
import {ProfileService} from '../../profile/profile-service/profile.service';
import {Router} from "@angular/router";
import {NotificationService} from "../../notifications/notification.service";
import {AppService} from "../../../services/app.service";
import {map} from "rxjs/operators";
import {BacklogModel} from "../../../models/application/backlog.model";
import {not} from "rxjs/internal-compatibility";

@Component({
    selector: 'app-backlog-modal',
    templateUrl: './backlog-modal.component.html',
    styleUrls: ['./backlog-modal.component.scss']
})
export class BacklogModalComponent {
    closeResult: string;
    modalReference: any;
    message = '';

    constructor(private ngbModal: NgbModal,
                private appService: AppService,
                private notificationService: NotificationService) {
    }

    open(content) {
        this.appService.getUnauthorized("/home/backlog")
            .pipe(map(response => response.json()))
            .subscribe(data => {
                console.log(data);
                this.message = this.map(data);
            });

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

    private map(notes: Array<BacklogModel>): string{
        let message: string = '';
        for(let note of notes){
            message += note.version != null ? note.version : '---';
            message += '  -  ';
            message += note.note != null ? note.note : '---';
            message += '\n';
        }
        return message;
    }
}