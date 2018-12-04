import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable} from "rxjs";
import {SocketMessage} from "../../../../models/socket/socket-message.model";
import {SocketMessageType} from "../../../../models/socket/socket-message-type.enum";
import {AppService} from "../../../../services/app.service";

@Component({
    selector: 'app-chat',
    templateUrl: './chat.component.html',
    styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

    conversation = [];

    textMessage = "";
    @Output() chatMessageEmitter: EventEmitter<SocketMessage> = new EventEmitter();
    @Input() chatMessageReceiver: Observable<string>;

    constructor(private baseService: AppService) {
    }

    ngOnInit() {
        this.chatMessageReceiver.subscribe((message: string) => this.collectMessage(message))
    }

    sendMessage() {
        let message = new SocketMessage();
        message.type = SocketMessageType.CHAT;
        message.chatMessage = this.textMessage;
        let date = new Date();
        message.date = date.getHours() + ':' + date.getMinutes();
        message.sender = this.baseService.accountModel.nick;
        this.textMessage = null;
        this.chatMessageEmitter.emit(message);
    }

    collectMessage(message: string) {
        this.conversation.push(message);
    }
}
