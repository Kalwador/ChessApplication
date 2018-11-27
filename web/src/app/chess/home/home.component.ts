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
            this.notificationService.trace('zalogowany!!!');
        }

        let a1 = new ArticleModel();
        a1.img = 'https://images.chesscomfiles.com/uploads/v1/news/469204.dfe6ad12.630x354o.9bab41affc88.jpeg';
        a1.header = 'Ding Liren Breaks Mikhail Tal\'s 95-Game Undefeated Streak';
        a1.text = 'Today, Ding Liren of China bested Mikhail Tals legendary streak of 95 games played without a defeat.' +
            'Ding Lirens unbeaten streak now stands at 96 and counting. Tals mark was set between October of 1973 and ' +
            'October of 1974. Ding Lirens streak is ongoing. He is currently playing in the Shenzhen Masters. ' +
            'Dings record over the course of his streak is 28 wins and 68 draws.';
        this.articles.push(a1);

        let a2 = new ArticleModel();
        a2.img = 'https://images.chesscomfiles.com/uploads/v1/news/469022.24ad42c0.630x354o.b34b87466994.png';
        a2.header = '8 Teams Qualify For 2019 PRO Chess League Season';
        a2.text = 'Last year, two new teams (the Armenia Eagles and the Chengdu Pandas) made it to the finals weekend in San Francisco' +
            ' where the Eagles eventually defeated the Pandas in a thrilling multiple-overtime battle. This year, six new teams and two ' +
            'returning favorites qualified for the 2019 PRO Chess League. Will any of them be this years champions?';
        this.articles.push(a2);

        let a3 = new ArticleModel();
        a3.img = 'https://images.chesscomfiles.com/uploads/v1/article/23396.d9326ad5.630x354o.0940a4f724ea.jpeg';
        a3.header = 'What If Carlsen And Caruana Were Martial Arts Fighters?';
        a3.text = 'One of the leading Russian newspapers, "Izvestia," has published an interview with the president of the Russian Chess ' +
            'Federation and captain of the Russian Olympic team, Andrey Filatov. This interview got my attention when Filatov claimed that ' +
            'Fabiano Caruanas chances to beat Magnus Carlsen in the upcoming world chess championship are 60 to 40!';
        this.articles.push(a3);
    }

    ngOnInit() {
        // this.homeService.getGreeting().subscribe(data => {
        //     this.message = data;
        //     this.notificationService.trace('Test polaczenia z api: ' + this.message);
        // });
    }
}
