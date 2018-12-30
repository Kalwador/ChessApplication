package com.chess.spring.engine.moves;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.Move;

public class ErrorMove extends Move {

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
    public Board execute() {
        throw new RuntimeException("cannot execute null moves!");
    }

    @Override
    public String toString() {
        return "Null MoveImpl";
    }
}
