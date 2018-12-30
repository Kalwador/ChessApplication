package com.chess.spring.engine.moves.simple.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.moves.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PawnMove extends Move {

    public PawnMove(Board board, AbstractPiece piece, int destination) {
        super(board, piece, destination);
    }

    @Override
    public String toString() {
        return BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }
}