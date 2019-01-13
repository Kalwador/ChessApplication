package com.chess.spring.game.moves.simple.pawn;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.attack.AbstractAttackMove;
import com.chess.spring.game.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PawnAttackAbstractMove extends AbstractAttackMove {

    public PawnAttackAbstractMove(Board board, AbstractPiece attacker, int destination, AbstractPiece attacked) {
        super(board, attacker, destination, attacked);
    }

    @Override
    public String toString() {
        return BoardService.INSTANCE.getPositionAtCoordinate(getPiece().getPosition()).substring(0, 1) + "x" +
                BoardService.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}