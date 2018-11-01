package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.status.GameEndStatus;
import com.chess.spring.models.status.GameWinner;

import java.util.List;

public interface GamePvEService {

    GamePvE getById(Long gameId) throws InvalidDataException;

    Long startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException;

    MoveDTO makeMove(Long gameId, MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError;

    MoveDTO makeFirstMove(Long gameId) throws InvalidDataException, DataMissmatchException, LockedSourceException;

    void stopGame(Long gameId);

    void handleEndOfGame(GamePvE game, Board board, GameEndStatus gameEndStatus, boolean isPlayerMove) throws LockedSourceException;

    GameWinner getWinner(Long gameId) throws InvalidDataException;

    List<MoveDTO> getLegateMoves(Long gameId) throws InvalidDataException;
}
