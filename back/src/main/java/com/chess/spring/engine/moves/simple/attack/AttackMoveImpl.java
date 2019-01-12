package com.chess.spring.engine.moves.simple.attack;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AttackMoveImpl extends AbstractAttackMove {

    public AttackMoveImpl(Board board, AbstractPiece pieceMoved, int destinationCoordinate, AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public String toString() {
        return getPiece().getType() + getSimpleFile() + "x" + BoardService.INSTANCE.getPositionAtCoordinate(getDestination());
    }
}