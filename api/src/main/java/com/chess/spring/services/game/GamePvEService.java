package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTOPvE;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.exceptions.*;

import java.util.List;

public interface GamePvEService {

    GamePvE getById(Long gameId) throws ResourceNotFoundException;

    Long startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException;

    MoveDTOPvE makeMove(Long gameId, MoveDTOPvE moveDTOPvE) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError, ResourceNotFoundException;

    void stopGame(Long gameId);

    List<MoveDTOPvE> getLegateMoves(Long gameId) throws ResourceNotFoundException;

    void reload() throws ResourceNotFoundException;
}
