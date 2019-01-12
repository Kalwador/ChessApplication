package com.chess.spring.communication.chat;

import com.chess.spring.communication.sockets.SocketMessageDTO;
import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.exceptions.ResourceNotFoundException;

public interface ChatService {
    Chat buildChat(GamePvP game);

    void addMessage(Long gameId, String message) throws ResourceNotFoundException;

    ChatDTO getConversation(Long gameId) throws ResourceNotFoundException;

    String buildMessage(SocketMessageDTO message);
}
