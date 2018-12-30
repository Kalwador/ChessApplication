package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.Move;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public abstract class AbstractPiece {

    private PieceType type;
    private int position;
    private PieceColor color;
    private boolean isFirstMove;
    private int code;

    AbstractPiece(PieceType type,
                  PieceColor color,
                  int position,
                  boolean isFirstMove) {
        this.type = type;
        this.position = position;
        this.color = color;
        this.isFirstMove = isFirstMove;
        this.code = calculateCode();
    }

    public PieceColor getPieceAllegiance() {
        return this.color;
    }

    public int getPieceValue() {
        return this.type.getPieceValue();
    }

    public abstract int locationBonus();

    public abstract AbstractPiece movePiece(Move move);

    public abstract Collection<Move> getOptionalMoves(Board board);

    private int calculateCode() {
        int result = this.type.hashCode();
        result = 31 * result + this.color.hashCode();
        result = 31 * result + this.position;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
    }
}