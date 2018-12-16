import {Component, Input, OnInit} from '@angular/core';
import {ProfileService} from "../profile-service/profile.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {NotificationService} from "../../notifications/notification.service";
import {AppService} from "../../../services/app.service";
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
    }

    upload() {
        // this.progress.percentage = 0;
        //
        // this.currentFileUpload = this.selectedFile.item(0);
        // this.profileService.updateAvatar(this.currentFileUpload).subscribe(
        //     event => {
        //         if (event.type === HttpEventType.UploadProgress) {
        //             this.progress.percentage = Math.round(100 * event.loaded / event.total);
        //         } else if (event instanceof HttpResponse) {
        //             this.notificationService.info('Zaktualizowano avatar!');
        //             //todo event i reload profilu
        //         }
        //     });
        // this.selectedFile = undefined;

        let options: any = {
            reportProgress: true,
            responseType: 'text',
            headers: {'Authorization': 'bearer ' + ""}
        };

        const uploadData = new FormData();
        uploadData.append('myFile', this.selectedFile, this.selectedFile.name);
        this.http.post('my-backend.com/file-upload', uploadData, options).pipe(map(response => response))


            .subscribe(event => {
                console.log(event); // handle event here
            });
    }
}
