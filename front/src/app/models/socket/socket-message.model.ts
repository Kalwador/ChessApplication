import {Move} from "../chess/move";
import {SocketMessageTypeEnum} from "./socket-message-type.enum";

export class SocketMessageModel {
    type: SocketMessageTypeEnum;
    moveDTO: Move;
    chatMessage: string;
    sender: string;
    thumbnail: string;
    date: string;
}