package com.chess.spring.models.status;


import com.chess.spring.exceptions.DataMissmatchException;

public enum GamePvPStatus {
    CHAT, BLACK_MOVE, WHITE_MOVE, OVER;

    public static GamePvPStatus convert(Integer status) throws DataMissmatchException {
        switch (status){
            case 0: {
                return CHAT;
            }
            case 1: {
                return BLACK_MOVE;
            }
            case 2: {
                return WHITE_MOVE;
            }
            case 3: {
                return OVER;
            }
        }
        throw new DataMissmatchException();
    }
}
