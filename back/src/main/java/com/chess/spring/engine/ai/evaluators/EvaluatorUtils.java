package com.chess.spring.engine.ai.evaluators;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorUtils {

    private static List<List<Boolean>> initColumns() {
         List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardUtils.INSTANCE.FIRST_COLUMN);
        columns.add(BoardUtils.INSTANCE.SECOND_COLUMN);
        columns.add(BoardUtils.INSTANCE.THIRD_COLUMN);
        columns.add(BoardUtils.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.EIGHTH_COLUMN);
        return columns;
    }

    public static int[] createPiecesOnColumnTable( Board board) {
        List<List<Boolean>> columns = initColumns();
         int[] piecesOnColumnTable = new int[columns.size()];
        for( Piece piece : board.getAllPieces()) {
            for(int i = 0 ; i < columns.size(); i++) {
                if(columns.get(i).get(piece.getPiecePosition())) {
                    piecesOnColumnTable[i]++;
                }
            }
        }
        return piecesOnColumnTable;
    }
}
