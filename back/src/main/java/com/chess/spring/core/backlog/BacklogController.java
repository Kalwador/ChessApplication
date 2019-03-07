package com.chess.spring.core.backlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/home/backlog")
public class BacklogController {

    private BacklogService backlogService;

    @Autowired
    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @GetMapping
    public List<Backlog> getAll() {
        return this.backlogService.getAll();
    }
}
