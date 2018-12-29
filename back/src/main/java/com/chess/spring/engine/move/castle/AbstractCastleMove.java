package com.chess.spring.engine.move.castle;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.Rook;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class AbstractCastleMove extends Move {
    private Rook castleRook;
    private int castleRookStart;
    private int castleRookDestination;

    AbstractCastleMove(Board board, AbstractPiece pieceMoved, int destinationCoordinate, Rook castleRook,
                       int castleRookStart, int castleRookDestination) {
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
        builder.setMoveMaker(getBoard().currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }
}