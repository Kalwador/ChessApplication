package com.chess.spring.engine.moves.simple.castle;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.Rook;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KingSideCastleMove extends AbstractCastleMove {

    public KingSideCastleMove(Board board, AbstractPiece piece, int destination, Rook rook, int rookSource, int rookDestination) {
        super(board, piece, destination, rook, rookSource, rookDestination);
    }

    @Override
    public String toString() {
        return "O-O";
    }

}