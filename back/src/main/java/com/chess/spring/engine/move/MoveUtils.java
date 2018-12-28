package com.chess.spring.engine.move;


import com.chess.spring.engine.move.simple.Move;

public enum MoveUtils {

    INSTANCE;

    public static int exchangeScore( Move move) {
        if(move == MoveFactory.getNullMove()) {
            return 1;
        }
        return move.isAttack() ?
                5 * exchangeScore(move.getBoard().getTransitionMove()) :
                exchangeScore(move.getBoard().getTransitionMove());
    }

}
