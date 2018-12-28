package com.chess.spring.engine.move;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.simple.Move;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class MoveTransition {
    private Move move;
    private MoveStatus status;
    private Board beforeMoveBoard;
    private Board afterMoveBoard;
}