package com.chess.spring.engine.moves.simple.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PawnAbstractMove extends AbstractMove {

    public PawnAbstractMove(Board board, AbstractPiece piece, int destination) {
        super(board, piece, destination);
    }

    @Override
    public String toString() {
        return BoardService.INSTANCE.getPositionAtCoordinate(getDestination());
    }
}