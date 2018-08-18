package com.chess.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "test controller", description = "Greet you at start")
public class TestController {

    @ApiOperation(value = "Returns test Greating")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "That really works"),
            @ApiResponse(code = 404, message = "Nope just illusion")
    })
    @GetMapping(path = "/")
    public String test(){
        return "Yo!";
    }

    @GetMapping(value = "/home")
    public String sayHello2() {
        return "home";
    }

    @GetMapping(value = "/home/111")
    public String sayHello22() {
        return "home111";
    }

    @GetMapping(value = "/login")
    public String sayHello33() {
        return "oauth";
    }

    @GetMapping(value = "/register/test")
    public String sayHello34() {
        return "register";
    }


    @GetMapping(value = "/cos")
    public String sayHello35() {
        return "cos";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user")
    public String sayHello3() {
        return "user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin")
    public String sayHello4() {
        return "admin";
    }

}
