package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.classic.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
