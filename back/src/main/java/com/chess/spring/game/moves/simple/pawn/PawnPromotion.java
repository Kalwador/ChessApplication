package com.chess.spring.game.moves.simple.pawn;

import com.chess.spring.exceptions.NotExpectedError;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardBuilder;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Pawn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PawnPromotion extends PawnAbstractMove {
    private AbstractMove move;
    private Pawn pawn;
    private AbstractPiece abstractPiece;

    public PawnPromotion(AbstractMove move,
                         AbstractPiece abstractPiece) {
        super(move.getBoard(), move.getPiece(), move.getDestination());
        this.move = move;
        this.pawn = (Pawn) move.getPiece();
        this.abstractPiece = abstractPiece;
    }

    @Override
    public Board execute() throws NotExpectedError {
        Board pawnMovedBoard = this.move.execute();
        BoardBuilder builder = new BoardBuilder();
        pawnMovedBoard.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.pawn.equals(piece)).forEach(builder::setPiece);
        pawnMovedBoard.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.abstractPiece.movePiece(this));
        builder.setMoveMaker(pawnMovedBoard.getCurrentPlayer().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public boolean isAttackMove() {
        return this.move.isAttackMove();
    }

    @Override
    public AbstractPiece getAttackedPiece() {
        return this.move.getAttackedPiece();
    }

    @Override
    public String toString() {
        return BoardService.getPositionAtCoordinate(getPiece().getPosition()) + "-" +
                BoardService.getPositionAtCoordinate(getDestination()) + "=" + this.abstractPiece.getType();
    }

}
