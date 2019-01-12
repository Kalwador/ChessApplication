package com.chess.spring.profile.register;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j
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
