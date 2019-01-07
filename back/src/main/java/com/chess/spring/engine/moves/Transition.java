package com.chess.spring.engine.moves;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.AbstractMove;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class Transition {
    private AbstractMove move;
    private MoveStatus status;
    private Board beforeMoveBoard;
    private Board afterMoveBoard;
}