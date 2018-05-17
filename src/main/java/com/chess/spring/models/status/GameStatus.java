package com.chess.spring.models.status;

import com.chess.spring.exceptions.game.GameStatusException;

public enum GameStatus {
    BLACK_MOVE, WHITE_MOVE, DONE;

    public static GameStatus convert(Integer status) {
        switch (status){
            case 0: {
                return BLACK_MOVE;
            }
            case 1: {
                return WHITE_MOVE;
            }
            case 2: {
                return DONE;
            }
        }
        throw new GameStatusException();
    }
}
