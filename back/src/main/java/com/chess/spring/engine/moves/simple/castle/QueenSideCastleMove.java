package com.chess.spring.engine.moves.simple.castle;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.Rook;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class QueenSideCastleMove extends AbstractCastleMove {

    public QueenSideCastleMove(Board board, AbstractPiece piece, int queenDestination, Rook rook,
                               int rookSource, int rookDestination) {
        super(board, piece, queenDestination, rook, rookSource, rookDestination);
    }

    @Override
    public String toString() {
        return "O-O-O";
    }

}