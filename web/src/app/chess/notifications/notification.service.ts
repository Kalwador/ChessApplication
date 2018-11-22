import {Inject, Injectable} from "@angular/core";
import {MAT_SNACK_BAR_DATA, MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBar} from "@angular/material";

@Injectable({
    providedIn: 'root',
})
export class NotificationService {
    private duration: number = 1400;

    constructor(public snackBar: MatSnackBar) {
    }

    public trace(message: string) {
        console.log(message);
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationType.TRACE, message]
        });
    }

    public info(message: string) {
        console.log(message);
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationType.INFO, message]
        });
    }

    public warning(message: string) {
        console.log(message);
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationType.WARNING, message]
        });
    }

    public danger(message: string) {
        console.log(message);
        this.snackBar.openFromComponent(NotificationComponent, {
            duration: this.duration,
            data: [NotificationType.DANGER, message]
        });
    }
}

import {Component} from '@angular/core';
import {NotificationType} from "../../models/notification/notification-type.enum";

@Component({
    selector: 'notification',
    templateUrl: './notification.component.html',
    styleUrls: ['./notification.component.scss']
})
export class NotificationComponent {
    constructor(@Inject(MAT_SNACK_BAR_DATA) public data: NotificationType) {
    }
}
