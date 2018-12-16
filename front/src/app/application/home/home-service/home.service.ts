import {Injectable} from '@angular/core';
import {AppService} from '../../../services/app.service';

@Injectable()
export class HomeService {
    localPath = '/home';

    constructor(private baseService: AppService) {
    }

    isLogedIn(): boolean {
        return this.baseService.isLoggedIn();
    }
}

