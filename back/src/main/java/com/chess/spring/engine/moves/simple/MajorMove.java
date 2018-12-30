package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MajorMove extends Move {

    public MajorMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate) {
        super(board, pieceMoved, destinationCoordinate);
    }

    @Override
    public String toString() {
        return getPiece().getPieceType().toString() + disambiguationFile() + BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}