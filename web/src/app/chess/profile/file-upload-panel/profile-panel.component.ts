import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile-service/profile.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {NotificationService} from "../../notifications/notification.service";

// @Component({
//   selector: 'app-profile-panel',
//   templateUrl: './profile-game-panel.component.html',
//   styleUrls: ['./profile-game-panel.component.css']
// })
export class ProfilePanelComponent implements OnInit {

    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = {percentage: 0};

    constructor(private profileService: ProfileService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
    }

    selectFile(event) {
        this.selectedFiles = event.target.files;
    }

    upload() {
        this.progress.percentage = 0;

        this.currentFileUpload = this.selectedFiles.item(0)
        this.profileService.pushFileToStorage(this.currentFileUpload).subscribe(
            event => {
                if (event.type === HttpEventType.UploadProgress) {
                    this.progress.percentage = Math.round(100 * event.loaded / event.total);
                } else if (event instanceof HttpResponse) {
                    this.notificationService.info('File is completely uploaded!');
                }
            });

        this.selectedFiles = undefined
    }
}
