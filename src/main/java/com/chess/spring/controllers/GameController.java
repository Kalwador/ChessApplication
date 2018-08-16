package com.chess.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/game")
@Api(value = "Game Controller", description = "Manage game transitions and statuses")
public class GameController {

//    private GameService gameService;
//
//    public GameController(GameService gameService) {
//        this.gameService = gameService;
//    }
//
//    @GetMapping(value = "/checkStatus/userID/{userID}")
//    public GameStatusDTO checkStatus(@PathVariable Long gameID,@PathVariable Long userID ){
//        return this.gameService.getGameStatusDTO(userID);
//    }
//
//    @PutMapping
//    public void makeMove(@RequestBody @Valid MoveDTO moveDTO){
//        this.gameService.makeMove(moveDTO);
//    }

}
