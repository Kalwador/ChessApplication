package com.chess.spring.models.status;

import com.chess.spring.exceptions.game.GameStatusException;

public enum GamePvEStatus {
    READY, WAIT, PLAYER_MOVE, OVER;

    public static GamePvEStatus convert(Integer status) {
        switch (status){
            case 0: {
                return READY;
            }
            case 1: {
                return WAIT;
            }
            case 2: {
                return PLAYER_MOVE;
            }
            case 3: {
                return OVER;
            }
        }
        throw new GameStatusException();
    }
}
