import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile-service/profile.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.css']
})
export class ProfilePanelComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 }

  constructor(private profileService: ProfileService) { }

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
          console.log('File is completely uploaded!');
        }
      });

    this.selectedFiles = undefined
  }
}
