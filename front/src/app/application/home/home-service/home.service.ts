import {Injectable} from '@angular/core';
import {AppService} from '../../../services/app.service';
import {Observable} from "rxjs";
import {PageModel} from "../../../models/application/page.model";

@Injectable()
export class HomeService {
    homePath = '/home';
    articlePath = '/articles'

    constructor(private appService: AppService) {
    }

    public getArticles(page: number, size: number): Observable<any> {
        let paging = this.appService.getPaging(page, size);
        return this.appService.getUnauthorized(this.homePath + this.articlePath + paging);
    }

    isLogedIn(): boolean {
        return this.appService.isLoggedIn();
    }
}

