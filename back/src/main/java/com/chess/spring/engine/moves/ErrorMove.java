package com.chess.spring.engine.moves;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.exceptions.NotExpectedError;

public class ErrorMove extends AbstractMove {

    public ErrorMove() {
        super(null, -1);
    }

    @Override
    public int getCurrentCoordinate() {
        return -1;
    }

    @Override
    public int getDestination() {
        return -1;
    }

    @Override
    public Board execute() throws NotExpectedError {
        throw new NotExpectedError("Error Move");
    }

    @Override
    public String toString() {
        return "Error Move";
    }
}
