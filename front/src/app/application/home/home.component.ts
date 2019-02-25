import {Component, OnInit} from '@angular/core';
import {HomeService} from './home-service/home.service';
import {ArticleModel} from '../../models/article.model';
import {NotificationService} from "../notifications/notification.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    message: string;
    articles = Array<ArticleModel>();

    constructor(private homeService: HomeService,
                private notificationService: NotificationService) {
        if (this.homeService.isLogedIn()) {
            this.notificationService.trace('Użytkownik został zalogowany!');
        }

        this.homeService.getArticles(0, 6).subscribe(data => {
            this.articles = data.json().content;
        })
    }

    ngOnInit() {
    }
}
