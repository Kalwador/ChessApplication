package com.chess.spring.game.pve;

import com.chess.spring.game.moves.MoveDTO;
import com.chess.spring.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GamePvEService {

    GamePvE getById(Long gameId) throws ResourceNotFoundException;

    Long startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException;

    MoveDTO makeMove(Long gameId, MoveDTO moveDTOPvE) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError, ResourceNotFoundException;

    void stopGame(Long gameId);

    List<MoveDTO> getLegateMoves(Long gameId) throws ResourceNotFoundException;

    Page<GamePvEDTO> getAll(Pageable pageable);

    void forfeit(Long gameId) throws ResourceNotFoundException;
}
