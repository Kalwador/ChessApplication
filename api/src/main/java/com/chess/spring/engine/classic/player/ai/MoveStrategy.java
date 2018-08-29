package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
