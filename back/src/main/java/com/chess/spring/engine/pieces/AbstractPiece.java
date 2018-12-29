package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.simple.Move;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode
public abstract class AbstractPiece {

    private PieceType pieceType;
    private PieceColor pieceColor;
    private int piecePosition;
    private boolean isFirstMove;
    private int cachedHashCode;

    AbstractPiece(PieceType type,
                  PieceColor pieceColor,
                  int piecePosition,
                  boolean isFirstMove) {
        this.pieceType = type;
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    public PieceColor getPieceAllegiance() {
        return this.pieceColor;
    }

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public abstract int locationBonus();

    public abstract AbstractPiece movePiece(Move move);

    public abstract Collection<Move> calculateLegalMoves(Board board);

    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceColor.hashCode();
        result = 31 * result + this.piecePosition;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
    }



}