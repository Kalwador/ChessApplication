package com.chess.spring.engine.moves;


import com.chess.spring.engine.moves.simple.AbstractMove;

public enum MoveUtils {

    INSTANCE;

    public static int exchangeScore( AbstractMove move) {
        if(move == MoveService.getNullMove()) {
            return 1;
        }
        return move.isAttackMove() ?
                5 * exchangeScore(move.getBoard().getTransition()) :
                exchangeScore(move.getBoard().getTransition());
    }

}
