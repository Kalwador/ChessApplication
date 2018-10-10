package com.chess.spring.controllers.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.exceptions.DataMissmatchException;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.exceptions.LockedSourceException;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GameWinner;
import com.chess.spring.services.game.GamePvEServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/game/pve")
@Api(value = "GamePvE PVE Controller", description = "Manage game with computer transitions and statuses")
public class GamePvEController {

    private GamePvEServiceImpl gameService;

    public GamePvEController(GamePvEServiceImpl gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GamePvEDTO getTestGame() {
        GamePvEDTO gamePvEDTO = new GamePvEDTO();
        gamePvEDTO.setColor(PlayerColor.BLACK);
        gamePvEDTO.setLevel(1);
        return gamePvEDTO;
    }

    @PostMapping(value = "/new")
    public GamePvEDTO startNewGame(@RequestBody @Valid GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        return gameService.startNewGame(gamePvEDTO);
    }

    @GetMapping(value = "/{gameId}")
    public MoveDTO getFirstMove(@PathVariable Long gameId) throws InvalidDataException, DataMissmatchException, LockedSourceException {
        return gameService.makeFirstMove(gameId);
    }

    @PostMapping(value = "/{gameId}")
    public MoveDTO makeMove(@PathVariable Long gameId, @RequestBody MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException {
        return gameService.makeMove(gameId, moveDTO);
    }

    @GetMapping(value = "/{gameId}/winner")
    public GameWinner getWinner(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getWinner(gameId);
    }
}
