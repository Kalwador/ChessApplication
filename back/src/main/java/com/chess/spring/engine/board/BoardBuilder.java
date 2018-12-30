package com.chess.spring.engine.board;

import com.chess.spring.engine.moves.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.Pawn;
import com.chess.spring.engine.pieces.PieceColor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BoardBuilder {

    private Map<Integer, AbstractPiece> configuration;
    private PieceColor nextPlayer;
    private Pawn pawn;
    private Move move;

    public BoardBuilder() {
        this.configuration = new HashMap<>(33, 1.0f);
    }

    public BoardBuilder setPiece(final AbstractPiece piece) {
        this.configuration.put(piece.getPosition(), piece);
        return this;
    }

    public BoardBuilder setMoveMaker(final PieceColor nextMoveMaker) {
        this.nextPlayer = nextMoveMaker;
        return this;
    }

    public BoardBuilder setPawn(final Pawn pawn) {
        this.pawn = pawn;
        return this;
    }

    public BoardBuilder setMoveTransition(final Move transitionMove) {
        this.move = transitionMove;
        return this;
    }

    public Board build() {
        return new Board(this);
    }

}