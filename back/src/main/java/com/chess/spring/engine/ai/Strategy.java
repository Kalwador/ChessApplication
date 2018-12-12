package com.chess.spring.engine.ai;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.Move;

public interface Strategy {
    Move execute(Board board);
}
