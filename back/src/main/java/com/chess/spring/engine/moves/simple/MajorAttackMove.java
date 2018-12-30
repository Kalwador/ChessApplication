package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MajorAttackMove extends AbstractAttackMove {

    public MajorAttackMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate, AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public String toString() {
        return getPiece().getType() + disambiguationFile() + "x" + BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}