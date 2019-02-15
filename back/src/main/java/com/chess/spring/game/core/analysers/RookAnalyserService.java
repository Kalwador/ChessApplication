package com.chess.spring.game.core.analysers;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.player.AbstractPlayer;
import com.chess.spring.game.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RookAnalyserService {

    private static int OPEN_COLUMN_ROOK_BONUS = 25;

    public int rookStructureScore(Board board,
                                  AbstractPlayer player) {
        List<Integer> rookLocations = calculateRookLocations(player);
        return calculateOpenFileRookBonus(board, rookLocations);
    }

    private static List<Integer> calculateRookLocations(AbstractPlayer player) {
        Builder<Integer> playerRookLocations = new Builder<>();
        player.getActivePieces().stream()
                .filter(piece -> piece.getType().isRook())
                .forEach(rook -> playerRookLocations.add(rook.getPosition()));
        return playerRookLocations.build();
    }

    private int calculateOpenFileRookBonus(Board board, List<Integer> rookLocations) {
        int bonus = 0;
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
        int[] piecesOnColumnTable = new int[BoardConfiguration.getInstance().getColumns().size()];
        for (AbstractPiece piece : board.getAllPieces()) {
            for (int i = 0; i < BoardConfiguration.getInstance().getColumns().size(); i++) {
                if (BoardConfiguration.getInstance().getColumns().get(i).get(piece.getPosition())) {
                    piecesOnColumnTable[i]++;
                }
            }
        }
        return piecesOnColumnTable;
    }


}
