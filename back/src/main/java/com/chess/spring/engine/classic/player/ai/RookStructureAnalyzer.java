package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.classic.player.player.AbstractPlayer;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.ArrayList;
import java.util.List;

public  class RookStructureAnalyzer {

    private static  RookStructureAnalyzer INSTANCE = new RookStructureAnalyzer();
    private static   List<List<Boolean>> BOARD_COLUMNS = initColumns();
    private static  int OPEN_COLUMN_ROOK_BONUS = 25;
    private static  int NO_BONUS = 0;

    private RookStructureAnalyzer() {
    }

    public static RookStructureAnalyzer get() {
        return INSTANCE;
    }

    private static  List<List<Boolean>> initColumns() {
         List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardUtils.INSTANCE.FIRST_COLUMN);
        columns.add(BoardUtils.INSTANCE.SECOND_COLUMN);
        columns.add(BoardUtils.INSTANCE.THIRD_COLUMN);
        columns.add(BoardUtils.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.EIGHTH_COLUMN);
        return ImmutableList.copyOf(columns);
    }

    public int rookStructureScore( Board board,
                                   AbstractPlayer player) {
         List<Integer> rookLocations = calculateRookLocations(player);
        return calculateOpenFileRookBonus(board, rookLocations);
    }

    private static List<Integer> calculateRookLocations( AbstractPlayer player) {
         Builder<Integer> playerRookLocations = new Builder<>();
        for( AbstractPiece piece : player.getActivePieces()) {
            if(piece.getPieceType().isRook()) {
                playerRookLocations.add(piece.getPiecePosition());
            }
        }
        return playerRookLocations.build();
    }

    private static int calculateOpenFileRookBonus( Board board,
                                                   List<Integer> rookLocations) {
        int bonus = NO_BONUS;
        for( Integer rookLocation : rookLocations) {
             int[] piecesOnColumn = createPiecesOnColumnTable(board);
             int rookColumn = rookLocation/8;
            for(int i = 0; i < piecesOnColumn.length; i++) {
                if(piecesOnColumn[i] == 1 && i == rookColumn){
                    bonus += OPEN_COLUMN_ROOK_BONUS;
                }

            }
        }
        return bonus;
    }

    private static int[] createPiecesOnColumnTable( Board board) {
         int[] piecesOnColumnTable = new int[BOARD_COLUMNS.size()];
        for( AbstractPiece piece : board.getAllPieces()) {
            for(int i = 0 ; i < BOARD_COLUMNS.size(); i++) {
                if(BOARD_COLUMNS.get(i).get(piece.getPiecePosition())) {
                    piecesOnColumnTable[i]++;
                }
            }
        }
        return piecesOnColumnTable;
    }


}
