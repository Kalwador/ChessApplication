package com.chess.spring.engine.ai.analyzers;

import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.player.Player;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public class KingSafetyAnalyzer {

    private static KingSafetyAnalyzer INSTANCE = new KingSafetyAnalyzer();

    public static KingSafetyAnalyzer get() {
        return INSTANCE;
    }


    public PieceDistance calculateKingTropism(Player player) {
        int playerKingSquare = player.getPlayerKing().getPiecePosition();
        Collection<Move> enemyMoves = player.getOpponent().getLegalMoves();
        Piece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for (Move move : enemyMoves) {
            int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestinationCoordinate());
            if (currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getMovedPiece();
            }
        }
        return new PieceDistance(closestPiece, closestDistance);
    }

    private int calculateChebyshevDistance(int kingTileId,
                                           int enemyAttackTileId) {

        int squareOneRank = getRank(kingTileId);
        int squareTwoRank = getRank(enemyAttackTileId);

        int squareOneFile = getFile(kingTileId);
        int squareTwoFile = getFile(enemyAttackTileId);

        int rankDistance = Math.abs(squareTwoRank - squareOneRank);
        int fileDistance = Math.abs(squareTwoFile - squareOneFile);

        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(int coordinate) {
        if (BoardUtils.INSTANCE.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if (BoardUtils.INSTANCE.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if (BoardUtils.INSTANCE.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if (BoardUtils.INSTANCE.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if (BoardUtils.INSTANCE.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if (BoardUtils.INSTANCE.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if (BoardUtils.INSTANCE.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(int coordinate) {
        if (BoardUtils.INSTANCE.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if (BoardUtils.INSTANCE.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if (BoardUtils.INSTANCE.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if (BoardUtils.INSTANCE.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if (BoardUtils.INSTANCE.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if (BoardUtils.INSTANCE.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if (BoardUtils.INSTANCE.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if (BoardUtils.INSTANCE.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

}
