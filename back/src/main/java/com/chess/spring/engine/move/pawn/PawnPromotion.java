package com.chess.spring.engine.move.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.Pawn;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PawnPromotion extends PawnMove {
    private Move move;
    private Pawn pawn;
    private AbstractPiece abstractPiece;

    public PawnPromotion(Move move,
                         AbstractPiece abstractPiece) {
        super(move.getBoard(), move.getPiece(), move.getDestination());
        this.move = move;
        this.pawn = (Pawn) move.getPiece();
        this.abstractPiece = abstractPiece;
    }

    @Override
    public Board execute() {
        Board pawnMovedBoard = this.move.execute();
        Board.Builder builder = new Board.Builder();
        pawnMovedBoard.currentPlayer().getActivePieces().stream().filter(piece -> !this.pawn.equals(piece)).forEach(builder::setPiece);
        pawnMovedBoard.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.abstractPiece.movePiece(this));
        builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public boolean isAttack() {
        return this.move.isAttack();
    }

    @Override
    public AbstractPiece getAttackedPiece() {
        return this.move.getAttackedPiece();
    }

    @Override
    public String toString() {
        return BoardUtils.INSTANCE.getPositionAtCoordinate(getPiece().getPiecePosition()) + "-" +
                BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination()) + "=" + this.abstractPiece.getPieceType();
    }

}
