package com.chess.spring.controllers.game;

import com.chess.spring.dto.game.GamePvPDTO;
import com.chess.spring.exceptions.*;
import com.chess.spring.services.game.GamePvPServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(path = "/game/pvp")
@Api(value = "GamePvP Controller", description = "Manage game with other player transitions and statuses")
public class GamePvPController {


    private GamePvPServiceImpl gameService;

    public GamePvPController(GamePvPServiceImpl gameService) {
        this.gameService = gameService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succes get list of games")
    })
    @GetMapping
    public Page<GamePvPDTO> getAll(Pageable page) {
        return this.gameService.getAll(page);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })

    @GetMapping(value = "/{gameId}")
    public GamePvPDTO getById(@PathVariable Long gameId) throws InvalidDataException {
        return GamePvPDTO.map(gameService.getById(gameId));
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
}
