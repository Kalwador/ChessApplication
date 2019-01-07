package com.chess.spring.engine.core.algorithms;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.AbstractMove;

public interface AbstractAlgorithm {

    long getNumBoardsEvaluated();

    AbstractMove execute(Board board, int level);

}
