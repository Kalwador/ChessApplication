import {Move} from "../chess/move";
import {SocketMessageType} from "./socket-message-type.enum";

export class SocketMessage {
    type: SocketMessageType;
    moveDTO: Move;
    chatMessage: string;
    sender: string;
    date: string;
}