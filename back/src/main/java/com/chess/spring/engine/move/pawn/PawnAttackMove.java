package com.chess.spring.engine.move.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.simple.AbstractAttackMove;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PawnAttackMove extends AbstractAttackMove {

    public PawnAttackMove(Board board, AbstractPiece attacker, int destination, AbstractPiece attacked) {
        super(board, attacker, destination, attacked);
    }

    @Override
    public String toString() {
        return BoardUtils.INSTANCE.getPositionAtCoordinate(getPiece().getPiecePosition()).substring(0, 1) + "x" +
                BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}