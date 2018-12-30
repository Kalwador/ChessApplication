package com.chess.spring.engine.classic.player.ai.algorithms;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.Move;

public interface AbstractAlgorithm {

    long getNumBoardsEvaluated();

    Move execute(Board board, int level);

}
