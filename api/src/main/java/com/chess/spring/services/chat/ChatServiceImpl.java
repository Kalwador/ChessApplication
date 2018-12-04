package com.chess.spring.services.chat;

import com.chess.spring.dto.ChatDTO;
import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.entities.Chat;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;
    DateFormat chatDateFormat;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.chatDateFormat = new SimpleDateFormat("HH:mm");
    }

    private Chat getByGameId(Long gameId) throws ResourceNotFoundException {
        return this.chatRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.GAME_NOT_FOUND.getInfo()));
    }

    @Override
    public Chat buildChat(GamePvP game) {
        return Chat.builder()
                .game(game)
                .conversation("")
                .build();
    }

    @Override
    public void addMessage(Long gameId, String message) throws ResourceNotFoundException {
        Chat chat = getByGameId(gameId);
        chat.setConversation(chat.getConversation() + message + "&zwnj;");
        //TODO - optimalization - update only content
        chatRepository.save(chat);
    }

    public String buildMessage(SocketMessageDTO message) {
        StringBuilder builder = new StringBuilder();
        builder.append(message.getDate());
        builder.append(" ");
        builder.append(message.getSender());
        builder.append(": ");
        builder.append(message.getChatMessage().replace("&zwnj;", " "));
        return builder.toString();
    }

    @Override
    public ChatDTO getConversation(Long gameId) throws ResourceNotFoundException {
        String conversation = getByGameId(gameId).getConversation();
        if (conversation.isEmpty()) {
            return new ChatDTO(gameId);
        }
        return ChatDTO.map(gameId, Arrays.asList(conversation.split("&zwnj;")));
    }
}
