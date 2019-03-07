package com.chess.spring.core;

import com.chess.spring.core.application.ApplicationInfoDTO;
import com.chess.spring.exceptions.LockedSourceException;
import com.chess.spring.profile.BCryptEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/test")
@Profile({"dev", "test"})
@Api(value = "test controller", description = "Allow to test security levels")
public class TestController {

    public TestController() {
    }

    @ApiOperation(value = "Returns test greeting")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "That really works"),
            @ApiResponse(code = 404, message = "Nope just illusion")
    })
    @GetMapping()
    public String welcomeTest() {
        log.info("hello world");
        return "hello world";
    }

    @GetMapping(value = "/home")
    public ApplicationInfoDTO homeTest() throws LockedSourceException {
        throw new LockedSourceException("asd");
    }

    @GetMapping(value = "/home/111")
    public String homeWithSomethingTest() {
        return "Home with something message";
    }

    @GetMapping(value = "/login")
    public String loginTest() {
        return "tokens message";
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

    @GetMapping(value = "/home/bcrypt/{password}")
    public String getBcryptedString(@PathVariable String password){
        return BCryptEncoder.encode(password);
    }
}
