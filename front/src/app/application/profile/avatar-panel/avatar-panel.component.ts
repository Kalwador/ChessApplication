import {Component, Input, OnInit} from '@angular/core';
import {ProfileService} from "../profile-service/profile.service";
import {NotificationService} from "../../notifications/notification.service";
import {Http} from "@angular/http";
import {map} from "rxjs/operators";

@Component({
    selector: 'app-avatar-panel',
    templateUrl: './avatar-panel.component.html',
    styleUrls: ['./avatar-panel.component.scss']
})
export class AvatarPanelComponent implements OnInit {

    @Input() avatar: string;

    selectedFile: File;
    currentFileUpload: File;
    progress: { percentage: number } = {percentage: 0};

    constructor(
        private profileService: ProfileService,
        private notificationService: NotificationService,
        private http: Http) {
    }

    ngOnInit() {

    }

    selectFile(event) {
        this.selectedFile = event.target.files[0];
        console.log(this.selectedFile.name);
    }

    upload() {
        this.profileService.updateAvatar(this.selectedFile).subscribe(data => {
            console.log('koniec');
        })
    }
}
