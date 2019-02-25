package com.chess.spring.game.pve;

import com.chess.spring.exceptions.*;
import com.chess.spring.game.moves.MoveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GamePvEService {

    GamePvE getById(Long gameId) throws ResourceNotFoundException;

    Long startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException, InvalidDataException;

    MoveDTO makeMove(Long gameId, MoveDTO moveDTOPvE) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError, ResourceNotFoundException;

    void stopGame(Long gameId);

    List<MoveDTO> getLegateMoves(Long gameId) throws ResourceNotFoundException, InvalidDataException;

    Page<GamePvEDTO> getAll(Pageable pageable);

    void forfeit(Long gameId) throws ResourceNotFoundException;
}
