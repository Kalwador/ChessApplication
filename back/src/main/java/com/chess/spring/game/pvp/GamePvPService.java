package com.chess.spring.game.pvp;

import com.chess.spring.communication.chat.ChatDTO;
import com.chess.spring.game.moves.MoveDTO;
import com.chess.spring.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GamePvPService {
    Page<GamePvPDTO> getAll(Pageable page) throws ResourceNotFoundException;

    GamePvP getById(Long gameId) throws ResourceNotFoundException;

    String getBoardById(Long gameId) throws ResourceNotFoundException;

    Long findGame(GamePvPDTO gamePvPDTO) throws ResourceNotFoundException;

    void makeMove(Long gameId, MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, ResourceNotFoundException, PreconditionFailedException;

    List<MoveDTO> getLegateMoves(Long gameId) throws ResourceNotFoundException, LockedSourceException, PreconditionFailedException;

    void forfeit(Long gameId) throws ResourceNotFoundException, LockedSourceException, PreconditionFailedException;

    ChatDTO getChatConversation(Long gameId) throws ResourceNotFoundException;
}
