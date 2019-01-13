package com.chess.spring.game.moves.simple.castle;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Rook;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
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