import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {NotificationService} from "../../../notifications/notification.service";
import {CurrentGameService} from "../../service/current-game.service";
import {GameType} from "../../../../models/chess/game/game-type.enum";
import {ProfileService} from "../../../profile/profile-service/profile.service";
import {Move} from "../../../../models/chess/move";
import {GameStatus} from "../../../../models/chess/game/game-status.enum";
import {SocketMessage} from "../../../../models/socket/socket-message.model";
import {SocketMessageType} from "../../../../models/socket/socket-message-type.enum";
import {Subject} from "rxjs";

declare var SockJS: any;
declare var Stomp: any;

@Component({
    selector: 'app-play-pvp',
    templateUrl: './game-play-pvp.component.html',
    styleUrls: ['./game-play-pvp.component.scss']
})
export class GamePlayPvpComponent implements OnInit {

    whitePlayerNick: string;
    blackPlayerNick: string;

    isPlayerPlaying: boolean = false;
    isGameContinued: boolean = false;

    chatMessageReceivedEmitter: Subject<SocketMessage> = new Subject<SocketMessage>();

    private stompClient;
    private socket = null;

    constructor(private route: ActivatedRoute,
                private gameService: GameService,
                public currentGameService: CurrentGameService,
                private notificationService: NotificationService,
                private profileService: ProfileService,
                public router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.gameService.getGame(+params['id'], GameType.PVP).subscribe(data => {
                this.currentGameService.game = data;
                this.currentGameService.fields = this.gameService.createBoard(this.currentGameService.game.board);
                this.isGameContinued = this.currentGameService.checkIfGameContinued(this.currentGameService.game.status);

                this.getPlayersInfo();

                this.initializeWebSocketConnection();
            }, error => {
                if (error.status === 400) {
                    this.router.navigate(['/']);
                    this.notificationService.warning("Wystąpił błąd, nie odnaleziono gry")
                }
            });
        });
    }

    initializeWebSocketConnection() {
        this.socket = new SockJS(this.gameService.getBasePath() + '/socket');
        this.stompClient = Stomp.over(this.socket);
        let that = this;
        this.stompClient.connect({}, function (frame) {
            that.stompClient.subscribe('/channel/game/' + that.currentGameService.game.id, (message) => {
                that.onMessageReceived(message);
            });
        });
    }

    private onMessageReceived(payload: any) {
        var message: SocketMessage;
        message = JSON.parse(payload.body);

        switch (message.type) {
            case SocketMessageType.MOVE : {
                if (message.sender != this.gameService.getAccountModel().nick) {
                    this.currentGameService.executeMove(message.moveDTO);
                }
                break;
            }
            case SocketMessageType.CHAT : {
                this.chatMessageReceivedEmitter.next(message);
                break;
            }

        }
    }

    makeMove(move: Move) {
        if (this.isPlayerPlaying) {
            if (this.currentGameService.game.status === GameStatus.PLAYER_MOVE
                || this.currentGameService.game.status === GameStatus.ON_HOLD
                || this.currentGameService.game.status === GameStatus.CHECK) {
                let legateMove = this.currentGameService.getLegateMove(move.source, move.destination);
                if (legateMove !== null) {
                    if (this.currentGameService.game.status === GameStatus.CHECK) {
                        this.currentGameService.executeMove(move);
                    }

                    this.gameService.makeMove(move, this.currentGameService.game.id, GameType.PVP).subscribe(data => {
                        if (this.currentGameService.checkIfGameContinued(data.status)) {
                            if (this.currentGameService.game.status === GameStatus.CHECK) {
                                this.currentGameService.executeMove(move);
                            }
                            //TODO - nie potrzebny jest ruch komputera
                            //TODO trzeba to zoptymalizować

                            // let message = new SocketMessage();
                            // message. = ;
                            // message. = ;
                            // message. = ;
                            // message. = ;
                            // message. = ;

                        }
                        //TODO replace log
                    }, error => {
                        if (error.status === 400) {
                            this.notificationService.info("Błędny ruch");
                        }
                        if (error.status === 423) {
                            this.notificationService.info("Gra zakończona");
                        }
                    });
                } else {
                    this.notificationService.info("Błędny ruch");
                }
            }
        }
    }

    private getPlayersInfo() {
        if (this.currentGameService.game.white != null) {
            this.profileService.getNickById(this.currentGameService.game.white).subscribe(data => {
                this.whitePlayerNick = data;
                this.isPlayerInGame(data);

            });
        }
        if (this.currentGameService.game.black != null) {
            this.profileService.getNickById(this.currentGameService.game.black).subscribe(data => {
                this.blackPlayerNick = data;
                this.isPlayerInGame(data);
            });
        }
    }

    private isPlayerInGame(nick: string) {
        if (this.gameService.getAccountModel().nick === nick) {
            this.isPlayerPlaying = true;
        }
        this.notificationService.trace('game-play-pvp:isPlayerInGame() ' + this.isPlayerPlaying);
    }

    private sendChatMessage(message: SocketMessage) {
        this.notificationService.trace('Sending chat message');
        this.stompClient.send('/app/channel/game/' + this.currentGameService.game.id + '/chat', {}, JSON.stringify(message));
    }

    private sendMoveMessage(message: SocketMessage) {
        this.notificationService.trace('Sending move message');
        this.stompClient.send('/app/channel/game/' + this.currentGameService.game.id + '/move', {}, JSON.stringify(message));
    }
}
