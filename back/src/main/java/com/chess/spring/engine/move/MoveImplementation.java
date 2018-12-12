package com.chess.spring.engine.move;

import com.chess.spring.engine.board.BitBoard;
import com.chess.spring.models.pieces.PieceType;

public class MoveImplementation {

    private int currentPosition;
    private int destinationPosition;
    private PieceType movedPiece;

    public MoveImplementation(int currentPosition,
                              int destinationPosition,
                              PieceType moved) {
        this.currentPosition = currentPosition;
        this.destinationPosition = destinationPosition;
        this.movedPiece = moved;
    }

    @Override
    public String toString() {
        return BitBoard.getPositionAtCoordinate(this.currentPosition) + "-"
                + BitBoard.getPositionAtCoordinate(this.destinationPosition);
    }

    @Override
    public int hashCode() {
        return this.movedPiece.hashCode() +
                this.currentPosition +
                this.destinationPosition;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MoveImplementation)) {
            return false;
        }
        MoveImplementation otherMoveImplementation = (MoveImplementation) other;
        return (this.movedPiece == otherMoveImplementation.getMovedPiece())
                && (this.currentPosition == otherMoveImplementation.getCurrentPosition())
                && (this.destinationPosition == otherMoveImplementation.getDestinationPosition());
    }

    private int getDestinationPosition() {
        return this.destinationPosition;
    }

    private int getCurrentPosition() {
        return this.currentPosition;
    }

    public PieceType getMovedPiece() {
        return this.movedPiece;
    }

}
