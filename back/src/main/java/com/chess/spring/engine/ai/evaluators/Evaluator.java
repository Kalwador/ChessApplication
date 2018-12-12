package com.chess.spring.engine.ai.evaluators;

import com.chess.spring.engine.board.Board;

public interface Evaluator {

    int evaluate(Board board, int depth);

}
