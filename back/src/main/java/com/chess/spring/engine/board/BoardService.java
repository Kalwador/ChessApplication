package com.chess.spring.engine.board;

import com.chess.spring.engine.moves.ErrorMove;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.moves.Transition;
import com.chess.spring.engine.pieces.*;
import com.chess.spring.engine.pieces.utils.PieceType;
import com.google.common.collect.ImmutableMap;

import java.util.*;


public enum BoardService {

    INSTANCE;

    public final List<Boolean> FIRST_COLUMN = initColumn(0);
    public final List<Boolean> SECOND_COLUMN = initColumn(1);
    public final List<Boolean> THIRD_COLUMN = initColumn(2);
    public final List<Boolean> FOURTH_COLUMN = initColumn(3);
    public final List<Boolean> FIFTH_COLUMN = initColumn(4);
    public final List<Boolean> SIXTH_COLUMN = initColumn(5);
    public final List<Boolean> SEVENTH_COLUMN = initColumn(6);
    public final List<Boolean> EIGHTH_COLUMN = initColumn(7);
    public final List<Boolean> FIRST_ROW = initRow(0);
    public final List<Boolean> SECOND_ROW = initRow(8);
    public final List<Boolean> THIRD_ROW = initRow(16);
    public final List<Boolean> FOURTH_ROW = initRow(24);
    public final List<Boolean> FIFTH_ROW = initRow(32);
    public final List<Boolean> SIXTH_ROW = initRow(40);
    public final List<Boolean> SEVENTH_ROW = initRow(48);
    public final List<Boolean> EIGHTH_ROW = initRow(56);
    public final List<String> ALGEBRAIC_NOTATION = BoardUtils.initializeAlgebraicNotation();
    public final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
    public static final int START_TILE_INDEX = 0;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES = 64;

    private static List<Boolean> initColumn(int columnNumber) {
        final Boolean[] column = new Boolean[NUM_TILES];
        for (int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);
        return Arrays.asList(column);
    }

    private static List<Boolean> initRow(int rowNumber) {
        final Boolean[] row = new Boolean[NUM_TILES];
        for (int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);
        return Arrays.asList(row);
    }

    private Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }


    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= START_TILE_INDEX && coordinate < NUM_TILES;
    }

    public String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    public static boolean kingThreat(final AbstractMove move) {
        final Board board = move.getBoard();
        final Transition transition = board.getCurrentPlayer().makeMove(move);
        return transition.getAfterMoveBoard().getCurrentPlayer().isInCheck();
    }

    public static boolean isKingPawnTrap(final Board board,
                                         final King king,
                                         final int frontTile) {
        final AbstractPiece piece = board.getPiece(frontTile);
        return piece != null && piece.getType().isPawn() &&
                piece.getPieceAllegiance() != king.getPieceAllegiance();
    }

    public static int calculatePieceExchangeValue(final AbstractMove move) {
        final AbstractPiece movingPiece = move.getPiece();
        if (move.isAttackMove()) {
            final AbstractPiece attackedPiece = move.getAttackedPiece();
            return (attackedPiece.getPieceValue() - movingPiece.getPieceValue() + PieceType.KING.getPieceValue()) * 100;
        }
        return PieceType.KING.getPieceValue() - movingPiece.getPieceValue();
    }

    public static List<AbstractMove> lastNMoves(final Board board, int N) {
        final List<AbstractMove> moveHistory = new ArrayList<>();
        AbstractMove move = board.getTransition();
        int i = 0;
        while (move != ErrorMove.getInstance() && i < N) {
            moveHistory.add(move);
            move = move.getBoard().getTransition();
            i++;
        }
        return moveHistory;
    }

    public static boolean isEndGame(final Board board) {
        return board.getCurrentPlayer().isInCheckMate() ||
                board.getCurrentPlayer().isInStaleMate();
    }
}
