package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.classic.player.AbstractPlayer;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class KingSafetyAnalyzer {

    private static  KingSafetyAnalyzer INSTANCE = new KingSafetyAnalyzer();
    private static  List<List<Boolean>> COLUMNS = initColumns();

    private KingSafetyAnalyzer() {
    }

    public static KingSafetyAnalyzer get() {
        return INSTANCE;
    }

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
        return ImmutableList.copyOf(columns);
    }

    public KingDistance calculateKingTropism( AbstractPlayer player) {
         int playerKingSquare = player.getPlayerKing().getPiecePosition();
         Collection<Move> enemyMoves = player.getOpponent().getLegalMoves();
        AbstractPiece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for( Move move : enemyMoves) {
             int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestination());
            if(currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getPiece();
            }
        }
        return new KingDistance(closestPiece, closestDistance);
    }

    private int calculateChebyshevDistance( int kingTileId,
                                            int enemyAttackTileId) {

         int squareOneRank = getRank(kingTileId);
         int squareTwoRank = getRank(enemyAttackTileId);

         int squareOneFile = getFile(kingTileId);
         int squareTwoFile = getFile(enemyAttackTileId);

         int rankDistance = Math.abs(squareTwoRank - squareOneRank);
         int fileDistance = Math.abs(squareTwoFile - squareOneFile);

        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile( int coordinate) {
        if(BoardUtils.INSTANCE.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if(BoardUtils.INSTANCE.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if(BoardUtils.INSTANCE.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if(BoardUtils.INSTANCE.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if(BoardUtils.INSTANCE.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if(BoardUtils.INSTANCE.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if(BoardUtils.INSTANCE.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if(BoardUtils.INSTANCE.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank( int coordinate) {
        if(BoardUtils.INSTANCE.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if(BoardUtils.INSTANCE.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if(BoardUtils.INSTANCE.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if(BoardUtils.INSTANCE.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if(BoardUtils.INSTANCE.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if(BoardUtils.INSTANCE.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if(BoardUtils.INSTANCE.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if(BoardUtils.INSTANCE.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    static class KingDistance {

         AbstractPiece enemyPiece;
         int distance;

        KingDistance( AbstractPiece enemyDistance,
                      int distance) {
            this.enemyPiece = enemyDistance;
            this.distance = distance;
        }

        public AbstractPiece getEnemyPiece() {
            return enemyPiece;
        }

        public int getDistance() {
            return distance;
        }

    }

}
