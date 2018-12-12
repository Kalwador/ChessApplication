package com.chess.spring.engine.board;

import com.chess.spring.engine.classic.PieceColor;
import com.chess.spring.engine.move.Move;
import com.chess.spring.models.pieces.Pawn;
import com.chess.spring.models.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    Map<Integer, Piece> boardConfig;
    PieceColor nextMoveMaker;
    Pawn enPassantPawn;
    Move transitionMove;

    public BoardBuilder() {
        this.boardConfig = new HashMap<>(33, 1.0f);
    }

    public BoardBuilder setPiece(Piece piece) {
        this.boardConfig.put(piece.getPiecePosition(), piece);
        return this;
    }

    public BoardBuilder setMoveMaker(PieceColor nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
        return this;
    }

    public BoardBuilder setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
        return this;
    }

    public BoardBuilder setMoveTransition(Move transitionMove) {
        this.transitionMove = transitionMove;
        return this;
    }

    public Board build() {
        return new Board(this);
    }

}