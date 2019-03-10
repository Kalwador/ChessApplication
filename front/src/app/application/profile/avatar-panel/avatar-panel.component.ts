import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProfileService} from "../profile-service/profile.service";
import {NotificationService} from "../../notifications/notification.service";

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
        private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.reloadAvatar();
    }

    selectFile(event) {
        this.selectedFile = event.target.files[0];
        console.log(this.selectedFile.name);
    }

    upload() {
        this.profileService.updateAvatar(this.selectedFile).subscribe(data => {
            this.notificationService.info("Awatar został zaktualizowany");
            this.reloadAvatar();
        }, error => {
            //TODO
        })
    }

    reloadAvatar(){
        this.notificationService.trace('AvatarPanel-reloadAvatar()');
        this.profileService.getAvatar().subscribe(data => {
            this.notificationService.trace("Avatar znaleziony");
            this.avatar = data;
        }, error => {
            this.notificationService.trace("Avatar nie znaleziony");
            this.notificationService.trace(error);
            if(error.status === 400){
                this.notificationService.trace('Avatar nie znaleziony');
                this.avatar = null;
            }
        });
    }
}
