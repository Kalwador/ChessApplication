package com.chess.spring.engine.moves;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.Move;

public class MoveFactory {

    private static Move NULL_MOVE = new ErrorMove();

    private MoveFactory() {
        throw new RuntimeException("Not instantiatable!");
    }

    public static Move getNullMove() {
        return NULL_MOVE;
    }

    public static Move createMove(Board board, int currentCoordinate, int destinationCoordinate) {
        for (Move move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestination() == destinationCoordinate) {
                return move;
            }
        }
        return NULL_MOVE;
    }
}