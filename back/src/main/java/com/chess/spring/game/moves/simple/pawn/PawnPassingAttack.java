package com.chess.spring.game.moves.simple.pawn;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardBuilder;
import com.chess.spring.game.pieces.AbstractPiece;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PawnPassingAttack extends PawnAttackAbstractMove {

    public PawnPassingAttack(Board board,
                             AbstractPiece pieceMoved,
                             int destinationCoordinate,
                             AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        getBoard().getCurrentPlayer().getActivePieces().stream().filter(piece -> !getPiece().equals(piece)).forEach(builder::setPiece);
        getBoard().getCurrentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(builder::setPiece);
        builder.setPiece(getPiece().movePiece(this));
        builder.setMoveMaker(getBoard().getCurrentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }
}