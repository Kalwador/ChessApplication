package com.chess.spring;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
public class InjectData {

    @PostConstruct
    public void inject() {

    }
}
