package com.chess.spring.game.board;

import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.*;
import com.chess.spring.game.pieces.utils.PlayerColor;

import java.util.Arrays;
import java.util.List;
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

    public static List<AbstractMove> calculateLegalMoves(Board board, List<AbstractPiece> pieces) {
        return pieces.stream()
                .flatMap(piece -> piece.getOptionalMoves(board).stream())
                .collect(Collectors.toList());
    }

    public static List<AbstractPiece> calculateActivePieces(BoardBuilder builder, PlayerColor playerColor) {
        return builder.getConfiguration().values().stream()
                .filter(piece -> piece.getPieceAllegiance() == playerColor)
                .collect(Collectors.toList());
    }

    public static List<String> initializeAlgebraicNotation() {
        String[] algebraicNotation = new String[]{
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
        return Arrays.asList(algebraicNotation);
    }
}
