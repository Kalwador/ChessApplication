import {Injectable} from '@angular/core';
import {RegisterModel} from '../../models/register.model';
import {AppService} from '../../services/app.service';
import {map} from 'rxjs/operators';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class RegisterService {
    path = '/register';

    constructor(private appService: AppService) {
    }

    register(registerModel: RegisterModel): Observable<any> {
        return this.appService.postUnAuthorized(this.path, registerModel).pipe(map(response => response));
    }

    getFacebookPath(): string {
        this.appService.getUnAuthorized(this.path + '/facebook/getLoginUri').pipe(map(response => {
            if (response.status === 200) {
                return response.text();
            }
        }));
        return null;
    }
}
