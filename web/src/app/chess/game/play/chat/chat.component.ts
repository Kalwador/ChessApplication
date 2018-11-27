import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NotificationService} from "../../../notifications/notification.service";
import {Observable} from "rxjs";
import {SocketMessage} from "../../../../models/socket/socket-message.model";
import {SocketMessageType} from "../../../../models/socket/socket-message-type.enum";
import {BaseService} from "../../../../services/base.service";

@Component({
    selector: 'app-chat',
    templateUrl: './chat.component.html',
    styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

    textMessage = "";
    @Output() chatMessageEmitter: EventEmitter<SocketMessage> = new EventEmitter();
    @Input() chatMessageReceiver: Observable<SocketMessage>;

    constructor(private baseService: BaseService,
        private notificationService: NotificationService) {
    }

    sendMessage() {
        let message = new SocketMessage();
        message.type = SocketMessageType.CHAT;
        message.chatMessage = this.textMessage;
        message.date = Date.now().toString();
        message.sender = this.baseService.getAccountModel().nick;
        this.chatMessageEmitter.emit(message);
    }

    ngOnInit() {
        this.chatMessageReceiver.subscribe((message: SocketMessage) => this.collectMessage(message))
    }

    collectMessage(message: SocketMessage) {
        console.log("Masz wiadomość: " + message.chatMessage);
    }
}
