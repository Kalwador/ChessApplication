package com.chess.spring.controllers.socket;

import com.chess.spring.dto.game.SocketMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import static java.lang.String.format;

@Slf4j
@Controller
public class SocketEmitter {
    private SimpMessageSendingOperations messagingTemplate;

    public SocketEmitter(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void distributeMessage(String roomId, SocketMessageDTO message) {
        messagingTemplate.convertAndSend(format("/channel/game/%s", roomId), message);
    }
}
