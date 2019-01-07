package com.chess.spring.engine.moves.simple.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.attack.AbstractAttackMove;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
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