package com.chess.spring.engine.ai.analyzers;

import com.chess.spring.engine.ai.evaluators.EvaluatorUtils;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.player.Player;
import com.google.common.collect.ImmutableList.Builder;

import java.util.List;

public  class RookStructureAnalyzer {

    private static  RookStructureAnalyzer INSTANCE = new RookStructureAnalyzer();
    private static  int OPEN_COLUMN_ROOK_BONUS = 25;
    private static  int NO_BONUS = 0;

    private RookStructureAnalyzer() {
    }

    public static RookStructureAnalyzer get() {
        return INSTANCE;
    }

    public int rookStructureScore( Board board,
                                   Player player) {
         List<Integer> rookLocations = calculateRookLocations(player);
        return calculateOpenFileRookBonus(board, rookLocations);
    }

    private static List<Integer> calculateRookLocations( Player player) {
         Builder<Integer> playerRookLocations = new Builder<>();
        for( Piece piece : player.getActivePieces()) {
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
             int[] piecesOnColumn = EvaluatorUtils.createPiecesOnColumnTable(board);
             int rookColumn = rookLocation/8;
            for(int i = 0; i < piecesOnColumn.length; i++) {
                if(piecesOnColumn[i] == 1 && i == rookColumn){
                    bonus += OPEN_COLUMN_ROOK_BONUS;
                }

            }
        }
        return bonus;
    }

}
