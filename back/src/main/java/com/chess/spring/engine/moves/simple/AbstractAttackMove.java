package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class AbstractAttackMove extends Move {

    private AbstractPiece attackedPiece;

    public AbstractAttackMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate, AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate);
        this.attackedPiece = pieceAttacked;
    }

    @Override
    public boolean isAttack() {
        return true;
    }
}
