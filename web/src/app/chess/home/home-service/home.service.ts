import {Observable} from 'rxjs/internal/Observable';
import {Injectable} from '@angular/core';
import {BaseService} from '../../../services/base.service';

@Injectable()
export class HomeService {
    localPath = '/home';

    constructor(private baseService: BaseService) {
    }

    getGreeting(): Observable<string> {
        return this.baseService.mapTEXT(this.baseService.getUnAuthorized(this.localPath));
    }

    isLogedIn(): boolean {
        return this.baseService.isLogedIn();
    }
}

