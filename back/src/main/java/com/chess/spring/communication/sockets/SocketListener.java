package com.chess.spring.communication.sockets;

import com.chess.spring.communication.chat.ChatService;
import com.chess.spring.exceptions.*;
import com.chess.spring.game.pvp.GamePvPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Controller
public class SocketListener {

    private SocketEmitter socketEmitter;
    private GamePvPService gamePvPService;
    private ChatService chatService;

    @Autowired
    public SocketListener(SocketEmitter socketEmitter,
                          GamePvPService gamePvPService,
                          ChatService chatService) {
        this.socketEmitter = socketEmitter;
        this.gamePvPService = gamePvPService;
        this.chatService = chatService;
    }

    @EventListener
    public void connect(SessionConnectedEvent event) {
        log.trace("Received a new web sockets connection.");
    }

    @MessageMapping("/channel/game/{roomId}/join")
    public void join(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage,
                     SimpMessageHeaderAccessor headerAccessor) {
        log.trace("AbstractPlayer join to room");
    }

    @MessageMapping("/channel/game/{roomId}/chat")
    public void chatMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO message) throws ResourceNotFoundException {
        String convertedMessage = chatService.buildMessage(message);
        message.setChatMessage(convertedMessage);
        this.chatService.addMessage(Long.valueOf(roomId), convertedMessage);
        this.socketEmitter.distributeMessage(roomId, message);
    }

    @MessageMapping("/channel/game/{roomId}/moves")
    public void moveMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO message) throws
            DataMissmatchException, LockedSourceException, ResourceNotFoundException, InvalidDataException, PreconditionFailedException {
        this.gamePvPService.makeMove(Long.parseLong(roomId), message.getMoveDTO());
    }

    @EventListener
    public void disconect(SessionDisconnectEvent event) {
        log.trace("Disconnected a web sockets connection.");
    }
}
