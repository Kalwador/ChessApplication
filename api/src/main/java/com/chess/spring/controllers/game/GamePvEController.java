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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/game/pve")
@Api(value = "GamePvE PVE Controller", description = "Manage game with computer transitions and statuses")
public class GamePvEController {

    private GamePvEService gameService;

    public GamePvEController(GamePvEService gameService) {
        this.gameService = gameService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}")
    public GamePvEDTO getById(@PathVariable Long gameId) throws InvalidDataException {
        return GamePvEDTO.convert(gameService.getById(gameId));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New game created"),
            @ApiResponse(code = 404, message = "User not logged in"),
            @ApiResponse(code = 409, message = "Level out of scale")
    })
    @PostMapping(value = "/new")
    public Long startNewGame(@RequestBody @Valid GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        return gameService.startNewGame(gamePvEDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Move returned successfully"),
            @ApiResponse(code = 400, message = "Game not found, wrong id"),
            @ApiResponse(code = 409, message = "Error during creation of game, wrong status"),
            @ApiResponse(code = 423, message = "Game is over")
    })
    @GetMapping(value = "/{gameId}/first")
    public MoveDTO getFirstMove(@PathVariable Long gameId) throws InvalidDataException, DataMissmatchException, LockedSourceException {
        return gameService.makeFirstMove(gameId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Move execution success"),
            @ApiResponse(code = 400, message = "Game not found, wrong id"),
            @ApiResponse(code = 409, message = "Error during creation of game, wrong status"),
            @ApiResponse(code = 423, message = "Game is over"),
            @ApiResponse(code = 500, message = "Not recognized error")
    })
    @PostMapping(value = "/{gameId}")
    public MoveDTO makeMove(@PathVariable Long gameId, @RequestBody MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError {
        return gameService.makeMove(gameId, moveDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game winner found succesfully"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}/winner")
    public GameWinner getWinner(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getWinner(gameId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game board found successfully"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}/board")
    public String getBoard(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getById(gameId).toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game board found successfully"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}/legate")
    public List<MoveDTO> getLegateMoves(@PathVariable Long gameId) throws InvalidDataException {
        return gameService.getLegateMoves(gameId);
    }
}
