package com.chess.spring.engine.core.analysers;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RookAnalyserService {

    private static int OPEN_COLUMN_ROOK_BONUS = 25;
    private static int NO_BONUS = 0;
    private AnalyserService analyserService;

    private RookAnalyserService(AnalyserService analyserService) {
        this.analyserService = analyserService;
    }


    public int rookStructureScore(Board board,
                                  AbstractPlayer player) {
        List<Integer> rookLocations = calculateRookLocations(player);
        return calculateOpenFileRookBonus(board, rookLocations);
    }

    private static List<Integer> calculateRookLocations(AbstractPlayer player) {
        Builder<Integer> playerRookLocations = new Builder<>();
        for (AbstractPiece piece : player.getActivePieces()) {
            if (piece.getType().isRook()) {
                playerRookLocations.add(piece.getPosition());
            }
        }
        return playerRookLocations.build();
    }

    private int calculateOpenFileRookBonus(Board board,
                                           List<Integer> rookLocations) {
        int bonus = NO_BONUS;
        for (Integer rookLocation : rookLocations) {
            int[] piecesOnColumn = createPiecesOnColumnTable(board);
            int rookColumn = rookLocation / 8;
            for (int i = 0; i < piecesOnColumn.length; i++) {
                if (piecesOnColumn[i] == 1 && i == rookColumn) {
                    bonus += OPEN_COLUMN_ROOK_BONUS;
                }

            }
        }
        return bonus;
    }

    private int[] createPiecesOnColumnTable(Board board) {
        int[] piecesOnColumnTable = new int[analyserService.getColumns().size()];
        for (AbstractPiece piece : board.getAllPieces()) {
            for (int i = 0; i < analyserService.getColumns().size(); i++) {
                if (analyserService.getColumns().get(i).get(piece.getPosition())) {
                    piecesOnColumnTable[i]++;
                }
            }
        }
        return piecesOnColumnTable;
    }


}
