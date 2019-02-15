package com.chess.spring.game.pvp;

import com.chess.spring.communication.chat.ChatDTO;
import com.chess.spring.exceptions.*;
import com.chess.spring.game.moves.MoveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/game/pvp")
@Api(value = "GamePvP Controller", description = "Manage game with other player transitions and statuses")
public class GamePvPController {

    private GamePvPService gameService;

    public GamePvPController(GamePvPService gameService) {
        this.gameService = gameService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succes get list of games")
    })
    @GetMapping
    public Page<GamePvPDTO> getAll(Pageable page) throws ResourceNotFoundException {
        return this.gameService.getAll(page);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })

    @GetMapping(value = "/{gameId}")
    public GamePvPDTO getById(@PathVariable Long gameId) throws ResourceNotFoundException {
        return GamePvPDTO.map(gameService.getById(gameId));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Game created"),
            @ApiResponse(code = 404, message = "User not logged in"),
            @ApiResponse(code = 409, message = "Level out of scale")
    })
    @PostMapping(value = "/new")
    public Long newGame(@RequestBody @Valid GamePvPDTO gamePvPDTO) throws ResourceNotFoundException {
        return gameService.newGame(gamePvPDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game found"),
            @ApiResponse(code = 404, message = "User not logged in"),
            @ApiResponse(code = 409, message = "Level out of scale")
    })
    @PostMapping(value = "/find")
    public Long findGame(@RequestBody @Valid GamePvPDTO gamePvEDTO) throws ResourceNotFoundException {
        return gameService.findGame(gamePvEDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Move execution success"),
            @ApiResponse(code = 400, message = "Move is not acceptable"),
            @ApiResponse(code = 404, message = "Game not found, wrong id"),
            @ApiResponse(code = 409, message = "Error during creation of game, wrong status"),
            @ApiResponse(code = 412, message = "You are not player in this game"),
            @ApiResponse(code = 423, message = "Game is over"),
            @ApiResponse(code = 500, message = "Not recognized error")
    })
    @PostMapping(value = "/{gameId}")
    public void makeMove(@PathVariable Long gameId, @RequestBody MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, ResourceNotFoundException, PreconditionFailedException {
        gameService.makeMove(gameId, moveDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game board found successfully"),
            @ApiResponse(code = 404, message = "Game not found, wrong id"),
            @ApiResponse(code = 412, message = "You are not player in this game")
    })
    @GetMapping(value = "/{gameId}/legate")
    public List<MoveDTO> getLegateMoves(@PathVariable Long gameId) throws ResourceNotFoundException, LockedSourceException, PreconditionFailedException {
        return gameService.getLegateMoves(gameId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Game forfeited successfully"),
            @ApiResponse(code = 404, message = "Game not found, wrong id"),
            @ApiResponse(code = 412, message = "You are not player in this game")
    })
    @DeleteMapping(value = "/forfeit/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forfeit(@PathVariable Long gameId) throws ResourceNotFoundException, LockedSourceException, PreconditionFailedException {
        gameService.forfeit(gameId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game chat"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })

    @GetMapping(value = "/{gameId}/chat")
    public ChatDTO getChatConversation(@PathVariable Long gameId) throws ResourceNotFoundException {
        return gameService.getChatConversation(gameId);
    }
}
