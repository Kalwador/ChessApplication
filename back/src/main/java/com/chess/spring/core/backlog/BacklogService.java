package com.chess.spring.core.backlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BacklogService {

    private BacklogRepository backlogRepository;

    @Autowired
    public BacklogService(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    public List<Backlog> getAll() {
        return backlogRepository.findAll();
    }
}
