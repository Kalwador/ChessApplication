package com.chess.spring.engine.move.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.pieces.Pawn;
import com.chess.spring.engine.pieces.AbstractPiece;

public class PawnEnPassantAttack extends PawnAttackMove {

    public PawnEnPassantAttack(Board board,
                               AbstractPiece pieceMoved,
                               int destinationCoordinate,
                               AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
    }

    @Override
    public Board execute() {
        Board.Builder builder = new Board.Builder();
        getBoard().currentPlayer().getActivePieces().stream().filter(piece -> !getPiece().equals(piece)).forEach(builder::setPiece);
        getBoard().currentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(builder::setPiece);
        builder.setPiece(getPiece().movePiece(this));
        builder.setMoveMaker(getBoard().currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public Board undo() {
        Board.Builder builder = new Board.Builder();
        for (AbstractPiece piece : getBoard().getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setEnPassantPawn((Pawn) this.getAttackedPiece());
        builder.setMoveMaker(getBoard().currentPlayer().getAlliance());
        return builder.build();
    }

}