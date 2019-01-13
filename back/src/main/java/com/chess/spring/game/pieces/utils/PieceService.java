package com.chess.spring.game.pieces.utils;

import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.pieces.*;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public enum PieceService {

    INSTANCE;

    private Table<PlayerColor, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceService.createAllPossibleMovedQueens();
    private Table<PlayerColor, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceService.createAllPossibleMovedRooks();
    private Table<PlayerColor, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceService.createAllPossibleMovedKnights();
    private Table<PlayerColor, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceService.createAllPossibleMovedBishops();
    private Table<PlayerColor, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceService.createAllPossibleMovedPawns();

    public Pawn getMovedPawn(PlayerColor playerColor,
                             int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(playerColor, destinationCoordinate);
    }

    public Knight getMovedKnight(PlayerColor playerColor,
                                 int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(playerColor, destinationCoordinate);
    }

    public Bishop getMovedBishop(PlayerColor playerColor,
                                 int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(playerColor, destinationCoordinate);
    }

    public Rook getMovedRook(PlayerColor playerColor,
                             int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(playerColor, destinationCoordinate);
    }

    public Queen getMovedQueen(PlayerColor playerColor,
                               int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(playerColor, destinationCoordinate);
    }

    private static Table<PlayerColor, Integer, Pawn> createAllPossibleMovedPawns() {
        ImmutableTable.Builder<PlayerColor, Integer, Pawn> pieces = ImmutableTable.builder();
        for (PlayerColor playerColor : PlayerColor.values()) {
            for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
                pieces.put(playerColor, i, new Pawn(playerColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PlayerColor, Integer, Knight> createAllPossibleMovedKnights() {
        ImmutableTable.Builder<PlayerColor, Integer, Knight> pieces = ImmutableTable.builder();
        for (PlayerColor playerColor : PlayerColor.values()) {
            for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
                pieces.put(playerColor, i, new Knight(playerColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PlayerColor, Integer, Bishop> createAllPossibleMovedBishops() {
        ImmutableTable.Builder<PlayerColor, Integer, Bishop> pieces = ImmutableTable.builder();
        for (PlayerColor playerColor : PlayerColor.values()) {
            for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
                pieces.put(playerColor, i, new Bishop(playerColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PlayerColor, Integer, Rook> createAllPossibleMovedRooks() {
        ImmutableTable.Builder<PlayerColor, Integer, Rook> pieces = ImmutableTable.builder();
        for (PlayerColor playerColor : PlayerColor.values()) {
            for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
                pieces.put(playerColor, i, new Rook(playerColor, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<PlayerColor, Integer, Queen> createAllPossibleMovedQueens() {
        ImmutableTable.Builder<PlayerColor, Integer, Queen> pieces = ImmutableTable.builder();
        for (PlayerColor playerColor : PlayerColor.values()) {
            for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
                pieces.put(playerColor, i, new Queen(playerColor, i, false));
            }
        }
        return pieces.build();
    }

}
