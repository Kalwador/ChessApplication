package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
