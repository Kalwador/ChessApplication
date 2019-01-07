package com.chess.spring.engine.moves;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.AbstractMove;

public class MoveService {

    private static AbstractMove NULL_MOVE = new ErrorMove();

    private MoveService() {
        throw new RuntimeException("Not instantiatable!");
    }

    public static AbstractMove getNullMove() {
        return NULL_MOVE;
    }

    public static AbstractMove createMove(Board board, int currentCoordinate, int destinationCoordinate) {
        for (AbstractMove move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestination() == destinationCoordinate) {
                return move;
            }
        }
        return NULL_MOVE;
    }
}