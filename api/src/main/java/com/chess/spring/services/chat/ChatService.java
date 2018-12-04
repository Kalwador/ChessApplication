package com.chess.spring.services.chat;

import com.chess.spring.dto.ChatDTO;
import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.entities.Chat;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.exceptions.ResourceNotFoundException;

public interface ChatService {
    Chat buildChat(GamePvP game);

    void addMessage(Long gameId, String message) throws ResourceNotFoundException;

    ChatDTO getConversation(Long gameId) throws ResourceNotFoundException;

    String buildMessage(SocketMessageDTO message);
}
