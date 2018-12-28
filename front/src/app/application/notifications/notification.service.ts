import {Component, Inject, Injectable} from "@angular/core";
import {MAT_SNACK_BAR_DATA, MatSnackBar} from "@angular/material";
import {NotificationTypeEnum} from "../../models/notification/notification-type.enum";

@Injectable({
    providedIn: 'root',
})
export class NotificationService {
    private duration: number = 2000;
    public isDevProfile: boolean = false;

    constructor(public snackBar: MatSnackBar) {
    }

    public trace(message: string) {
        if (this.isDevProfile) {
            console.log('TRACE:' + message);
            // this.snackBar.openFromComponent(NotificationComponent, {
            //     duration: this.duration,
            //     data: [NotificationTypeEnum.TRACE, message]
            // });
        }
    }

    public info(message: string) {
        if (this.isDevProfile) {
            console.log('INFO:' + message);
        }
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationTypeEnum.INFO, message]
        });
    }

    public warning(message: string) {
        if (this.isDevProfile) {
            console.log('WARNING:' + message);
        }
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationTypeEnum.WARNING, message]
        });
    }

    public danger(message: string) {
        if (this.isDevProfile) {
            console.log('ERROR:' + message);
        }
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationTypeEnum.DANGER, message]
        });
    }

    test(param: any) {
        console.log('TEST:' + param.klass);
        console.log('TEST:' + param);
    }
}

@Component({
    selector: 'notification',
    templateUrl: './notification.component.html',
    styleUrls: ['./notification.component.scss']
})
export class NotificationComponent {
    constructor(@Inject(MAT_SNACK_BAR_DATA) public data: NotificationTypeEnum) {
    }
}
