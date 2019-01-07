package com.chess.spring.engine.board;

import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.*;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BoardUtils {

    public static Board createStandardBoardImpl() {
        final BoardBuilder builder = new BoardBuilder();
        builder.setPiece(new Rook(PlayerColor.BLACK, 0));
        builder.setPiece(new Knight(PlayerColor.BLACK, 1));
        builder.setPiece(new Bishop(PlayerColor.BLACK, 2));
        builder.setPiece(new Queen(PlayerColor.BLACK, 3));
        builder.setPiece(new King(PlayerColor.BLACK, 4, true, true));
        builder.setPiece(new Bishop(PlayerColor.BLACK, 5));
        builder.setPiece(new Knight(PlayerColor.BLACK, 6));
        builder.setPiece(new Rook(PlayerColor.BLACK, 7));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 8));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 9));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 10));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 11));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 12));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 13));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 14));
        builder.setPiece(new Pawn(PlayerColor.BLACK, 15));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 48));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 49));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 50));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 51));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 52));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 53));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 54));
        builder.setPiece(new Pawn(PlayerColor.WHITE, 55));
        builder.setPiece(new Rook(PlayerColor.WHITE, 56));
        builder.setPiece(new Knight(PlayerColor.WHITE, 57));
        builder.setPiece(new Bishop(PlayerColor.WHITE, 58));
        builder.setPiece(new Queen(PlayerColor.WHITE, 59));
        builder.setPiece(new King(PlayerColor.WHITE, 60, true, true));
        builder.setPiece(new Bishop(PlayerColor.WHITE, 61));
        builder.setPiece(new Knight(PlayerColor.WHITE, 62));
        builder.setPiece(new Rook(PlayerColor.WHITE, 63));
        builder.setMoveMaker(PlayerColor.WHITE);
        return builder.build();
    }

    public static Collection<AbstractMove> calculateLegalMoves(Board board, Collection<AbstractPiece> pieces) {
        return pieces.stream()
                .flatMap(piece -> piece.getOptionalMoves(board)
                        .stream())
                .collect(Collectors.toList());
    }

    public static Collection<AbstractPiece> calculateActivePieces(BoardBuilder builder, PlayerColor playerColor) {
        return builder.getConfiguration().values().stream()
                .filter(piece -> piece.getPieceAllegiance() == playerColor)
                .collect(Collectors.toList());
    }
}
