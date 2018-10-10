package com.chess.spring.models.status;

import com.chess.spring.exceptions.DataMissmatchException;

public enum GamePvEStatus {
    READY, ON_HOLD, PLAYER_MOVE, OVER;

    public static GamePvEStatus convert(Integer status) throws DataMissmatchException {
        switch (status) {
            case 0:
                return READY;

            case 1:
                return ON_HOLD;

            case 2:
                return PLAYER_MOVE;

            case 3:
                return OVER;

        }
        throw new DataMissmatchException("Błędy statu gry PvE, status id: " + status);
    }
}
