import {Observable} from 'rxjs/internal/Observable';
import {Injectable} from '@angular/core';
import {AppService} from '../../../services/app.service';

@Injectable()
export class HomeService {
    localPath = '/home';

    constructor(private baseService: AppService) {
    }

    getGreeting(): Observable<string> {
        return this.baseService.mapTEXT(this.baseService.getUnAuthorized(this.localPath));
    }

    isLogedIn(): boolean {
        return this.baseService.isLoggedIn();
    }
}

