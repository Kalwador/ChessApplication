package com.chess.spring.game.moves.simple.castle;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Rook;
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