import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GameService} from "../../service/game.service";
import {NotificationService} from "../../../notifications/notification.service";
import {CurrentGameService} from "../../service/current-game.service";
import {GameType} from "../../../../models/chess/game/game-type.enum";
import {ProfileService} from "../../../profile/profile-service/profile.service";
import {Move} from "../../../../models/chess/move";
import {GameStatus} from "../../../../models/chess/game/game-status.enum";
import {SocketMessageModel} from "../../../../models/socket/socket-message.model";
import {SocketMessageTypeEnum} from "../../../../models/socket/socket-message-type.enum";
import {Subject} from "rxjs";
import {PlayerColor} from "../../../../models/chess/player-color.enum";

declare var SockJS: any;
declare var Stomp: any;

@Component({
    selector: 'app-play-pvp',
    templateUrl: './game-play-pvp.component.html',
    styleUrls: ['./game-play-pvp.component.scss']
})
export class GamePlayPvpComponent implements OnInit {

    whiteStatus: string = "";
    whitePlayerNick: string;

    blackStatus: string = "";
    blackPlayerNick: string;

    isPlayerPlaying: boolean = false;
    isGameContinued: boolean = false;

    chatMessageReceivedEmitter: Subject<string> = new Subject<string>();

    PlayerColor = PlayerColor;
    GameType = GameType;

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
                this.loadChatConversation();
                this.currentGameService.fields = this.gameService.createBoard(this.currentGameService.game.board);
                this.isGameContinued = this.currentGameService.checkIfGameContinued(this.currentGameService.game.status, true);
                this.handleGameStatus(this.currentGameService.game.status, false);
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

    reloadGame() {
        this.gameService.getGame(this.currentGameService.game.id, GameType.PVP).subscribe(data => {
            this.currentGameService.game = data;
            this.isGameContinued = this.currentGameService.checkIfGameContinued(this.currentGameService.game.status, true);
            this.getPlayersInfo();
        }, error => {
            if (error.status === 400) {
                this.router.navigate(['/']);
                this.notificationService.warning("Wystąpił błąd, nie odnaleziono gry")
            }
        });
    }

    loadChatConversation() {
        this.currentGameService.getChatConversation().subscribe(data => {
            data.conversation.forEach(message => {
                if (message !== '') {
                    this.chatMessageReceivedEmitter.next(message);
                }
            });
        });
    }

    initializeWebSocketConnection() {
        this.socket = new SockJS(this.gameService.getBasePath() + '/sockets');
        this.stompClient = Stomp.over(this.socket);
        let that = this;
        this.stompClient.connect({}, function (frame) {
            that.stompClient.subscribe('/channel/game/' + that.currentGameService.game.id, (message) => {
                that.onMessageReceived(message);
            });
        });
    }

    private onMessageReceived(payload: any) {
        var message: SocketMessageModel;
        message = JSON.parse(payload.body);

        switch (message.type) {
            case SocketMessageTypeEnum.MOVE : {
                if (message.sender != this.gameService.getAccountModel().nick) {
                    this.currentGameService.executeMove(message.moveDTO);
                    this.handleGameStatus(message.moveDTO.status, message.moveDTO.isInCheck);
                    this.isGameContinued = this.currentGameService.checkIfGameContinued(message.moveDTO.status, true);
                }
                break;
            }
            case SocketMessageTypeEnum.CHAT : {
                this.chatMessageReceivedEmitter.next(message.chatMessage);
                break;
            }
            case SocketMessageTypeEnum.START_GAME : {
                this.reloadGame();
                this.notificationService.info(message.chatMessage);
                break;
            }
            case SocketMessageTypeEnum.END_GAME : {
                this.isGameContinued = false;
                this.notificationService.info(message.chatMessage);
                break;
            }
        }
    }

    makeMove(move: Move) {
        if (this.isPlayerPlaying && this.isGameContinued) {
            if (this.currentGameService.game.status === GameStatus.WHITE_MOVE
                || this.currentGameService.game.status === GameStatus.BLACK_MOVE
                || this.currentGameService.game.status === GameStatus.ON_HOLD
                || this.currentGameService.game.status === GameStatus.CHECK) {
                let legateMove = this.currentGameService.getLegateMove(move.source, move.destination);
                if (legateMove !== null) {
                    if (this.currentGameService.game.status === GameStatus.CHECK) {
                        this.currentGameService.executeMove(move);
                    }
                    this.gameService.makeMove(move, this.currentGameService.game.id, GameType.PVP).subscribe(data => {
                        if (this.currentGameService.checkIfGameContinued(data.status, true)) {
                            this.currentGameService.getLegateMoves(GameType.PVP);
                        }
                        if (this.currentGameService.game.status === GameStatus.CHECK) {
                            this.currentGameService.executeMove(move);
                        }
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
            if (this.isGameContinued) {
                this.currentGameService.getLegateMoves(GameType.PVP);
            }
        }
        this.notificationService.trace('game-play-pvp:isPlayerInGame() ' + this.isPlayerPlaying);
    }

    public sendChatMessage(message: SocketMessageModel) {
        this.notificationService.trace('Sending chat message');
        this.stompClient.send('/app/channel/game/' + this.currentGameService.game.id + '/chat', {}, JSON.stringify(message));
    }

    public sendMoveMessage(message: SocketMessageModel) {
        this.notificationService.trace('Sending moves message');
        this.stompClient.send('/app/channel/game/' + this.currentGameService.game.id + '/moves', {}, JSON.stringify(message));
    }

    private handleGameStatus(status: string, isInCheck: boolean) {
        let temp = '';
        if (isInCheck) {
            temp = "Szach! ";
        }
        if (status === GameStatus.WHITE_MOVE) {
            temp = temp + this.gameService.translateStatus(status);
            this.whiteStatus = temp;
            this.blackStatus = "";
        }
        if (status === GameStatus.BLACK_MOVE) {
            temp = temp + this.gameService.translateStatus(status);
            this.blackStatus = temp;
            this.whiteStatus = "";
        }
    }
}
