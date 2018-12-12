package com.chess.spring.controllers;

import com.chess.spring.dto.ApplicationInfo;
import com.chess.spring.services.ApplicationService;
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
    public ApplicationInfo info() {
        return this.applicationService.getInfo();
    }
}
