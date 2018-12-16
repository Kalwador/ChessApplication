package com.chess.spring.engine.pieces;

import com.chess.spring.engine.classic.PieceColor;
import com.chess.spring.engine.board.BoardUtils;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceUtils {

    INSTANCE;

    private final Table<PieceColor, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleMovedQueens();
    private final Table<PieceColor, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleMovedRooks();
    private final Table<PieceColor, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();
    private final Table<PieceColor, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();
    private final Table<PieceColor, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();

    Pawn getMovedPawn(final PieceColor pieceColor,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(pieceColor, destinationCoordinate);
    }

    Knight getMovedKnight(final PieceColor pieceColor,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(pieceColor, destinationCoordinate);
    }

    Bishop getMovedBishop(final PieceColor pieceColor,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(pieceColor, destinationCoordinate);
    }

    Rook getMovedRook(final PieceColor pieceColor,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(pieceColor, destinationCoordinate);
    }

    Queen getMovedQueen(final PieceColor pieceColor,
                        final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(pieceColor, destinationCoordinate);
    }

    private static Table<PieceColor, Integer, Pawn> createAllPossibleMovedPawns() {
        final ImmutableTable.Builder<PieceColor, Integer, Pawn> pieces = ImmutableTable.builder();
        for(final PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Pawn(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Knight> createAllPossibleMovedKnights() {
        final ImmutableTable.Builder<PieceColor, Integer, Knight> pieces = ImmutableTable.builder();
        for(final PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Knight(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Bishop> createAllPossibleMovedBishops() {
        final ImmutableTable.Builder<PieceColor, Integer, Bishop> pieces = ImmutableTable.builder();
        for(final PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Bishop(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Rook> createAllPossibleMovedRooks() {
        final ImmutableTable.Builder<PieceColor, Integer, Rook> pieces = ImmutableTable.builder();
        for(final PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Rook(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Queen> createAllPossibleMovedQueens() {
        final ImmutableTable.Builder<PieceColor, Integer, Queen> pieces = ImmutableTable.builder();
        for(final PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Queen(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

}
