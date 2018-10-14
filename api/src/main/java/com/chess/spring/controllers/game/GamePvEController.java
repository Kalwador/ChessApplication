package com.chess.spring.controllers.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GameWinner;
import com.chess.spring.services.game.GamePvEService;
import com.chess.spring.utils.pgn.FenUtilities;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/game/pve")
@Api(value = "GamePvE PVE Controller", description = "Manage game with computer transitions and statuses")
public class GamePvEController {

    private GamePvEService gameService;

    public GamePvEController(GamePvEService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/{gameId}")
    public GamePvEDTO getById(@PathVariable Long gameId) throws InvalidDataException {
        return GamePvEDTO.convert(gameService.getById(gameId));
    }

    @PostMapping(value = "/new")
    public Long startNewGame(@RequestBody @Valid GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        return gameService.startNewGame(gamePvEDTO);
    }

    @GetMapping(value = "/{gameId}/first")
    public MoveDTO getFirstMove(@PathVariable Long gameId) throws InvalidDataException, DataMissmatchException, LockedSourceException {
        return gameService.makeFirstMove(gameId);
    }

    @PostMapping(value = "/{gameId}")
    public MoveDTO makeMove(@PathVariable Long gameId, @RequestBody MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError {
        return gameService.makeMove(gameId, moveDTO);
    }

    @GetMapping(value = "/{gameId}/winner")
    public GameWinner getWinner(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getWinner(gameId);
    }

    @GetMapping(value = "/{gameId}/board")
    public String getBoard(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getById(gameId).toString();
    }
}
