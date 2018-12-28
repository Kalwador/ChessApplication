package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.classic.player.AbstractPlayer;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.classic.player.ai.KingSafetyAnalyzer.KingDistance;
import com.google.common.annotations.VisibleForTesting;

public  class StandardBoardEvaluator
        implements BoardEvaluator {

    private  static int CHECK_MATE_BONUS = 10000;
    private  static int CHECK_BONUS = 50;
    private  static int CASTLE_BONUS = 60;
    private  static int MOBILITY_MULTIPLIER = 2;
    private  static int ATTACK_MULTIPLIER = 2;
    private  static int TWO_BISHOPS_BONUS = 50;
    private static  StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator() {
    }

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate( Board board,
                         int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    @VisibleForTesting
    private static int score( AbstractPlayer player,
                              int depth) {
        return mobility(player) +
               kingThreats(player, depth) +
               attacks(player) +
               castle(player) +
               pieceEvaluations(player) +
               pawnStructure(player);
    }

    private static int attacks( AbstractPlayer player) {
        int attackScore = 0;
        for( Move move : player.getLegalMoves()) {
            if(move.isAttack()) {
                 AbstractPiece movedPiece = move.getPiece();
                 AbstractPiece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static int pieceEvaluations( AbstractPlayer player) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for ( AbstractPiece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if(piece.getPieceType().isBishop()) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private static int mobility( AbstractPlayer player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int mobilityRatio( AbstractPlayer player) {
        return (int)((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int kingThreats( AbstractPlayer player,
                                    int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth) : check(player);
    }

    private static int check( AbstractPlayer player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int depthBonus( int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castle( AbstractPlayer player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int pawnStructure( AbstractPlayer player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety( AbstractPlayer player) {
         KingDistance kingDistance = KingSafetyAnalyzer.get().calculateKingTropism(player);
        return ((kingDistance.getEnemyPiece().getPieceValue() / 100) * kingDistance.getDistance());
    }

    private static int rookStructure( Board board,  AbstractPlayer player) {
        return RookStructureAnalyzer.get().rookStructureScore(board, player);
    }

}
