package com.chess.spring.engine.move;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.Move.MoveStatus;

public  class MoveTransition {

    private  Board fromBoard;
    private  Board toBoard;
    private  Move transitionMove;
    private  MoveStatus moveStatus;

    public MoveTransition( Board fromBoard,
                           Board toBoard,
                           Move transitionMove,
                           MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.transitionMove = transitionMove;
        this.moveStatus = moveStatus;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Board getToBoard() {
         return this.toBoard;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
