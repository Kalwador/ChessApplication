package com.chess.spring.services.game;

import com.chess.spring.models.game.PlayerColor;

import java.util.Random;

public abstract class GameService {

    protected PlayerColor drawColor(){
        return new Random().nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

}
