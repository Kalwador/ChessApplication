package com.chess.spring.controllers.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.dto.game.GamePvEStatusDTO;
import com.chess.spring.entities.GamePvE;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.services.game.GamePvEService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/game/pve")
@Api(value = "GamePvE PVE Controller", description = "Manage game with computer transitions and statuses")
public class GamePvEController {

    private GamePvEService gameService;

    public GamePvEController(GamePvEService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public GamePvEDTO startNewGame(@RequestBody @Valid GamePvEDTO gamePvEDTO){
        return gameService.startNewGame(gamePvEDTO);
    }

    @PostMapping
    public MoveDTO makeFirstMove(){
        return gameService.makeFirstMove();
    }

    @PostMapping
    public MoveDTO makeMove(@@RequestBody @Valid MoveDTO moveDTO){
        return gameService.makeMove(moveDTO);
    }

}
