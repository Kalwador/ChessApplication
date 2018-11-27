import {Component, OnInit} from '@angular/core';
import {BaseService} from "../../services/base.service";

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

    appInfo: string;

    constructor(private baseService: BaseService) {
    }

    ngOnInit() {
        this.appInfo = this.baseService.appInfo.projectName;
        this.appInfo += ', Profile: ';
        this.appInfo += this.baseService.appInfo.profile;
        this.appInfo += ', Version: ';
        this.appInfo += this.baseService.appInfo.version;
    }

}
