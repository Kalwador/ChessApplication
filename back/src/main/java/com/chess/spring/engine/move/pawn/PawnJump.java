package com.chess.spring.engine.move.pawn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.Pawn;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PawnJump extends Move {

    public PawnJump(Board board, Pawn pieceMoved, int destinationCoordinate) {
        super(board, pieceMoved, destinationCoordinate);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        getBoard().currentPlayer().getActivePieces().stream().filter(piece -> !getPiece().equals(piece)).forEach(builder::setPiece);
        getBoard().currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        Pawn movedPawn = (Pawn) getPiece().movePiece(this);
        builder.setPiece(movedPawn);
        builder.setPawn(movedPawn);
        builder.setMoveMaker(getBoard().currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}