package com.chess.spring.controllers;

import com.chess.spring.controllers.game.SocketController;
import com.chess.spring.dto.game.GamePvPDTO;
import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.models.game.SocketMessageType;
import com.chess.spring.services.game.GamePvPServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@Api(value = "test controller", description = "Allow to test security levels")
public class TestController {

    private SocketController socketController;
    private GamePvPServiceImpl gamePvPService;

    @Autowired
    public TestController(
            SocketController socketController,
            GamePvPServiceImpl gamePvPService) {
        this.socketController = socketController;
        this.gamePvPService = gamePvPService;
    }

    @ApiOperation(value = "Returns test Greating")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "That really works"),
            @ApiResponse(code = 404, message = "Nope just illusion")
    })
    @GetMapping(path = "/")
    public String welcomeTest(){
        return "message";
    }

    @GetMapping(value = "/home")
    public String homeTest() {
        return "Home message!";
    }

    @GetMapping(value = "/home/111")
    public String homeWithSomethingTest() {
        return "Home with something message";
    }

    @GetMapping(value = "/login")
    public String loginTest() {
        return "login message";
    }

    @GetMapping(value = "/register/test")
    public String registerTest() {
        return "register message";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user")
    public String sayHello3() {
        return "user message";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin")
    public String sayHello4() {
        return "admin message";
    }

    @GetMapping(path = "/message/{message}")
    public void testMessage(@PathVariable String message) {
        this.socketController.distributeChatMessage("1", new SocketMessageDTO(SocketMessageType.CHAT,null,message,"SEFVER"));
    }
}
