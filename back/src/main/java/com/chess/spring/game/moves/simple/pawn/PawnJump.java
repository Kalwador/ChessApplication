package com.chess.spring.game.moves.simple.pawn;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.board.BoardBuilder;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.Pawn;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PawnJump extends AbstractMove {

    public PawnJump(Board board, Pawn pieceMoved, int destinationCoordinate) {
        super(board, pieceMoved, destinationCoordinate);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        getBoard().getCurrentPlayer().getActivePieces().stream().filter(piece -> !getPiece().equals(piece)).forEach(builder::setPiece);
        getBoard().getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        Pawn movedPawn = (Pawn) getPiece().movePiece(this);
        builder.setPiece(movedPawn);
        builder.setPawn(movedPawn);
        builder.setMoveMaker(getBoard().getCurrentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardService.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}