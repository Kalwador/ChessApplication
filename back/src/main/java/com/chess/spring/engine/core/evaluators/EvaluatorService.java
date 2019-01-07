package com.chess.spring.engine.core.evaluators;

import com.chess.spring.engine.board.Board;

public interface EvaluatorService {
    int evaluate(Board board, int depth);
}
