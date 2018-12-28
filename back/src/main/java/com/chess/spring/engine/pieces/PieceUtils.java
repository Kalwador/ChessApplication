package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.BoardUtils;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceUtils {

    INSTANCE;

    private  Table<PieceColor, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleMovedQueens();
    private  Table<PieceColor, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleMovedRooks();
    private  Table<PieceColor, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();
    private  Table<PieceColor, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();
    private  Table<PieceColor, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();

    Pawn getMovedPawn( PieceColor pieceColor,
                       int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(pieceColor, destinationCoordinate);
    }

    Knight getMovedKnight( PieceColor pieceColor,
                           int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(pieceColor, destinationCoordinate);
    }

    Bishop getMovedBishop( PieceColor pieceColor,
                           int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(pieceColor, destinationCoordinate);
    }

    Rook getMovedRook( PieceColor pieceColor,
                       int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(pieceColor, destinationCoordinate);
    }

    Queen getMovedQueen( PieceColor pieceColor,
                         int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(pieceColor, destinationCoordinate);
    }

    private static Table<PieceColor, Integer, Pawn> createAllPossibleMovedPawns() {
         ImmutableTable.Builder<PieceColor, Integer, Pawn> pieces = ImmutableTable.builder();
        for( PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Pawn(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Knight> createAllPossibleMovedKnights() {
         ImmutableTable.Builder<PieceColor, Integer, Knight> pieces = ImmutableTable.builder();
        for( PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Knight(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Bishop> createAllPossibleMovedBishops() {
         ImmutableTable.Builder<PieceColor, Integer, Bishop> pieces = ImmutableTable.builder();
        for( PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Bishop(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Rook> createAllPossibleMovedRooks() {
         ImmutableTable.Builder<PieceColor, Integer, Rook> pieces = ImmutableTable.builder();
        for( PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Rook(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PieceColor, Integer, Queen> createAllPossibleMovedQueens() {
         ImmutableTable.Builder<PieceColor, Integer, Queen> pieces = ImmutableTable.builder();
        for( PieceColor pieceColor : PieceColor.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(pieceColor, i, new Queen(pieceColor, i, false));
            }
        }
        return pieces.build();
    }

}
