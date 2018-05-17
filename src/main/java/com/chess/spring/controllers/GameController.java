package com.chess.spring.controllers;

import com.chess.spring.services.GameService;
import com.chess.spring.dto.GameStatusDTO;
import com.chess.spring.dto.MoveDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "/game")
public class GameController {

    private GameService gameService;
    private PlayerController playerController;

    public GameController(GameService gameService, PlayerController playerController) {
        this.gameService = gameService;
        this.playerController = playerController;
    }

    @GetMapping(value = "/checkStatus/userID/{userID}")
    public GameStatusDTO checkStatus(@PathVariable Long gameID,@PathVariable Long userID ){
        return this.gameService.getGameStatusDTO(userID);
    }

    @PutMapping
    public void makeMove(@RequestBody @Valid MoveDTO moveDTO){
        this.gameService.makeMove(moveDTO);
    }
}
