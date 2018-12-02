package com.chess.spring.controllers.game;

import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.models.game.SocketMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static java.lang.String.format;

@Slf4j
@Controller
public class SocketController {
    private SimpMessageSendingOperations messagingTemplate;

    public SocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    /**
     * Enter to chat, step one
     * Levate the chat, last but one step
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection.");
    }

    /**
     * Send message, to other users
     */
    @MessageMapping("/channel/game/{roomId}/chat")
    public void distributeChatMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO message) {
        messagingTemplate.convertAndSend(format("/channel/game/%s/chat", roomId), message);
    }

    /**
     * Send message, to other users
     */
    @MessageMapping("/channel/game/{roomId}/move")
    public void distributeMoveMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO message) {
        messagingTemplate.convertAndSend(format("/channel/game/%s/move", roomId), message);
    }

    /**
     * User enter the chat, send info about joinning to other users
     * here u cna count nouber of observers of game
     */
    @MessageMapping("/channel/game/{roomId}/join")
    public void addUser(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
    }

    /**
     * Last step, decrease number of watcching the game players
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    }
}
