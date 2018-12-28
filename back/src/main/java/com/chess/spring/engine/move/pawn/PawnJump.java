package com.chess.spring.engine.move.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.Pawn;

public class PawnJump extends Move {

    public PawnJump(Board board, Pawn pieceMoved, int destinationCoordinate) {
        super(board, pieceMoved, destinationCoordinate);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof PawnJump && super.equals(other);
    }

    @Override
    public Board execute() {
        Board.Builder builder = new Board.Builder();
        getBoard().currentPlayer().getActivePieces().stream().filter(piece -> !getPiece().equals(piece)).forEach(builder::setPiece);
        getBoard().currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        Pawn movedPawn = (Pawn) getPiece().movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setMoveMaker(getBoard().currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}