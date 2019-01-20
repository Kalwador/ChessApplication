package com.chess.spring.game.moves.simple.pawn;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PawnAbstractMove extends AbstractMove {

    public PawnAbstractMove(Board board, AbstractPiece piece, int destination) {
        super(board, piece, destination);
    }

    @Override
    public String toString() {
        return BoardService.getPositionAtCoordinate(getDestination());
    }
}