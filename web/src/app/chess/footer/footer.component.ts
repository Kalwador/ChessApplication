import {Component, OnInit} from '@angular/core';
import {AppService} from "../../services/app.service";

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

    appInfo: string;

    constructor(private baseService: AppService) {
    }

    ngOnInit() {
        this.appInfo = this.baseService.appInfo.projectName;
        this.appInfo += ', Profile: ';
        this.appInfo += this.baseService.appInfo.profile;
        this.appInfo += ', Version: ';
        this.appInfo += this.baseService.appInfo.version;
    }

}
