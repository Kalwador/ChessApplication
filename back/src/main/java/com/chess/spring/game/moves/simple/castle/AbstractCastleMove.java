package com.chess.spring.game.moves.simple.castle;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardBuilder;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Rook;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractCastleMove extends AbstractMove {
    private Rook castleRook;
    private int castleRookStart;
    private int castleRookDestination;

    AbstractCastleMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
        super(board, pieceMoved, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookDestination = castleRookDestination;
    }

    @Override
    public boolean isCastlingMove() {
        return true;
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (AbstractPiece piece : getBoard().getAllPieces()) {
            if (!getPiece().equals(piece) && !this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(getPiece().movePiece(this));
        builder.setPiece(new Rook(this.castleRook.getPieceAllegiance(), this.castleRookDestination, false));
        builder.setMoveMaker(getBoard().getCurrentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }
}