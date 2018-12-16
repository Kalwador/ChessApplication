import {Component, OnInit} from '@angular/core';
import {AppService} from "../../services/app.service";

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

    constructor(public baseService: AppService) {
    }

    ngOnInit() {
    }

    showChangelog(){
        console.log(this.baseService.appInfo);
    }
}
