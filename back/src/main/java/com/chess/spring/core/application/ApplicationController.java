package com.chess.spring.core.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class ApplicationController {

    private ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(path = "/info")
    public ApplicationInfoDTO info() {
        return this.applicationService.getInfo();
    }

}
