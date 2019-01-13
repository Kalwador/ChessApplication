package com.chess.spring.game.moves;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.moves.simple.AbstractMove;
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