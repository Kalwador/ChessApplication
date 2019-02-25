package com.chess.spring.game.core.evaluators;

import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.game.board.Board;

public interface EvaluatorService {
    int evaluate(Board board, int level) throws InvalidDataException;
}
