package com.chess.spring.models.status;

import com.chess.spring.exceptions.room.RoomStatusNotValidException;

public enum RoomStatus {
    RUNNING, QUEUE, FREE;

    public static RoomStatus getStatus(Integer status) {
        switch (status){
            case 0:{
                return RUNNING;
            }
            case 1: {
                return QUEUE;
            }
            case 2: {
                return FREE;
            }
            default: {
                throw new RoomStatusNotValidException();
            }
        }
    }
}
