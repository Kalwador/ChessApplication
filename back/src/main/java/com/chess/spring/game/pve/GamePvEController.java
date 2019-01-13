package com.chess.spring.game.pve;

import com.chess.spring.exceptions.*;
import com.chess.spring.game.moves.MoveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
            @ApiResponse(code = 200, message = "Success get list of games")
    })
    @GetMapping
    public Page<GamePvEDTO> getAll(Pageable page){
        return this.gameService.getAll(page);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game"),
            @ApiResponse(code = 404, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}")
    public GamePvEDTO getById(@PathVariable Long gameId) throws ResourceNotFoundException {
        return GamePvEDTO.map(gameService.getById(gameId));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New game created"),
            @ApiResponse(code = 404, message = "User not logged in"),
            @ApiResponse(code = 409, message = "Level out of scale")
    })
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Long startNewGame(@RequestBody @Valid GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        return gameService.startNewGame(gamePvEDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Move execution success"),
            @ApiResponse(code = 400, message = "Move is not acceptable"),
            @ApiResponse(code = 404, message = "Game not found, wrong id"),
            @ApiResponse(code = 409, message = "Error during creation of game, wrong status"),
            @ApiResponse(code = 423, message = "Game is over"),
            @ApiResponse(code = 500, message = "Not recognized error")
    })
    @PostMapping(value = "/{gameId}")
    public MoveDTO makeMove(@PathVariable Long gameId, @RequestBody MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError, ResourceNotFoundException {
        return gameService.makeMove(gameId, moveDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game board found successfully"),
            @ApiResponse(code = 404, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}/board")
    public String getBoard(@PathVariable Long gameId) throws ResourceNotFoundException {
        return gameService.getById(gameId).toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game board found successfully"),
            @ApiResponse(code = 404, message = "Game not found, wrong id")
    })
    @GetMapping(value = "/{gameId}/legate")
    public List<MoveDTO> getLegateMoves(@PathVariable Long gameId) throws ResourceNotFoundException {
        List<MoveDTO> list = gameService.getLegateMoves(gameId);
        return list;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Game forfeited successfully"),
            @ApiResponse(code = 404, message = "Game not found, wrong id")
    })
    @DeleteMapping(value = "/forfeit/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forfeit(@PathVariable Long gameId) throws ResourceNotFoundException {
         gameService.forfeit(gameId);
    }

}
