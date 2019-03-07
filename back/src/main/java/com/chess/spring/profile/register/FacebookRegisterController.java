package com.chess.spring.profile.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/register/facebook")
public class FacebookRegisterController {

    private FacebookService registerService;

    public FacebookRegisterController(FacebookService facebookService) {
        this.registerService = facebookService;
    }

    @GetMapping("/getLoginUri")
    public String getLoginUri() {
        return this.registerService.getFacebookRegisterUri();
    }

    @GetMapping
    public void login(@RequestParam("code") String code, @RequestParam("state") String state) {
        registerService.facebookRegister(code, state);
    }

}
