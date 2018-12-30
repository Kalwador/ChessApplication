package com.chess.spring.engine.classic.player.ai.evaluators;

import com.chess.spring.engine.board.Board;

public interface EvaluatorService {
    int evaluate(Board board, int depth);
}
