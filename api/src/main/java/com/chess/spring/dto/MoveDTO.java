package com.chess.spring.dto;

import com.chess.spring.engine.classic.board.Move;

public class MoveDTO {
    private Integer source;
    private Integer destination;

    public static MoveDTO map(Move move) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        return moveDTO;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
