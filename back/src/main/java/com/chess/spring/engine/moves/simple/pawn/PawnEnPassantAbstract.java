package com.chess.spring.engine.moves.simple.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.pieces.Pawn;
import com.chess.spring.engine.pieces.AbstractPiece;

public class PawnEnPassantAbstract extends PawnAttackAbstractMove {

    public PawnEnPassantAbstract(Board board,
                                 AbstractPiece pieceMoved,
                                 int destinationCoordinate,
                                 AbstractPiece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof PawnEnPassantAbstract && super.equals(other);
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

    @Override
    public Board undo() {
        BoardBuilder builder = new BoardBuilder();
        for (AbstractPiece piece : getBoard().getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setPawn((Pawn) this.getAttackedPiece());
        builder.setMoveMaker(getBoard().getCurrentPlayer().getAlliance());
        return builder.build();
    }

}