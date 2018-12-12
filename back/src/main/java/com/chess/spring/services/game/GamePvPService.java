package com.chess.spring.services.game;

import com.chess.spring.dto.ChatDTO;
import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvPDTO;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.exceptions.*;
import org.hibernate.boot.model.source.spi.Sortable;
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
