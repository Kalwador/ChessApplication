import {Injectable} from '@angular/core';
import {RegisterModel} from '../../models/register.model';
import {Router} from '@angular/router';
import {BaseService} from '../../services/base.service';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class RegisterService {
    path = '/register';

    constructor(private baseService: BaseService,
                private router: Router) {
    }

    register(registerModel: RegisterModel) {
        this.baseService.postUnAuthorized(this.path, registerModel)
            .subscribe(data => {
                console.log('Pomyslnie zarejestrowano');
                this.router.navigate(['/']);
                //TODO-NOTIF-SERVICE
            }, error => {
            });
    }

    getFacebookPath(): string {
        this.baseService.getUnAuthorized(this.path + '/facebook/getLoginUri').pipe(map(response => {
            if (response.status === 200) {
                return response.text();
            }
        }));
        return null;
    }
}
