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
    public void distributeChatMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage) {
        messagingTemplate.convertAndSend(format("/channel/game/%s", roomId), chatMessage);
    }

    /**
     * Send message, to other users
     */
    @MessageMapping("/channel/game/{roomId}/move")
    public void makeMove(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage) {
        System.out.println(chatMessage);
    }

    /**
     * User enter the chat, send info about joinning to other users
     * here u cna count nouber of observers of game
     */
    @MessageMapping("/channel/game/{roomId}/join")
    public void addUser(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            SocketMessageDTO leaveMessage = new SocketMessageDTO();

            chatMessage.setType(SocketMessageType.JOIN);
            messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }

    /**
     * Last step, decrease number of watcching the game players
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("room_id");
        if (username != null) {
            log.info("User Disconnected: " + username);

            SocketMessageDTO chatMessage = new SocketMessageDTO();
            chatMessage.setType(SocketMessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
        }
    }
}
