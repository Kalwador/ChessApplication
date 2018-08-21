import {Observable} from 'rxjs/internal/Observable';
import {RestService} from '../../rest-service/rest.service';
import {Injectable, OnInit} from '@angular/core';

@Injectable()
export class HomeService {
    localPath = '/home';

    constructor(private restService: RestService) {
    }

    getGreeting(): Observable<string> {
        return this.restService.get(this.localPath);
    }
}

