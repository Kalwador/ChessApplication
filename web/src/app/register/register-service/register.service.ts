import {Injectable} from '@angular/core';
import {RegisterModel} from '../../models/register.model';
import {Router} from '@angular/router';
import {AppService} from '../../services/app.service';
import {map} from 'rxjs/operators';
import {NotificationService} from "../../chess/notifications/notification.service";

@Injectable({
    providedIn: 'root'
})
export class RegisterService {
    path = '/register';

    constructor(private baseService: AppService,
                private router: Router,
                private notificationService: NotificationService) {
    }

    register(registerModel: RegisterModel) {
        this.baseService.postUnAuthorized(this.path, registerModel)
            .subscribe(data => {
                this.notificationService.info('Pomyslnie zarejestrowano');
                this.router.navigate(['/']);
            }, error => {
                this.notificationService.danger(error.toString());
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
