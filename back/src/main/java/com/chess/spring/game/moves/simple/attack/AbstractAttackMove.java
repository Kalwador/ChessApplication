package com.chess.spring.game.moves.simple.attack;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class AbstractAttackMove extends AbstractMove {
    private AbstractPiece attackedPiece;

    public AbstractAttackMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate, AbstractPiece attackedPiece) {
        super(board, pieceMoved, destinationCoordinate);
        this.attackedPiece = attackedPiece;
    }

    @Override
    public boolean isAttackMove() {
        return true;
    }
}
