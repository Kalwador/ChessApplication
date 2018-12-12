package com.chess.spring.engine.ai.evaluators;

import com.chess.spring.engine.ai.analyzers.KingSafetyAnalyzer;
import com.chess.spring.engine.ai.analyzers.PawnStructureAnalyzer;
import com.chess.spring.engine.ai.analyzers.PieceDistance;
import com.chess.spring.engine.ai.analyzers.RookStructureAnalyzer;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.player.Player;
import com.google.common.annotations.VisibleForTesting;

public class EvaluatorImpl implements Evaluator {

    private static int CHECK_MATE_BONUS = 10000;
    private static int CHECK_BONUS = 50;
    private static int CASTLE_BONUS = 60;
    private static int MOBILITY_MULTIPLIER = 2;
    private static int ATTACK_MULTIPLIER = 2;
    private static int TWO_BISHOPS_BONUS = 50;
    private static EvaluatorImpl INSTANCE = new EvaluatorImpl();

    private EvaluatorImpl() {
    }

    public static EvaluatorImpl get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(Board board,int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    @VisibleForTesting
    private static int score(Player player, int depth) {
        return mobility(player) +
                kingThreats(player, depth) +
                attacks(player) +
                castle(player) +
                pieceEvaluations(player) +
                pawnStructure(player) +
                kingSafety(player);
    }

    private static int attacks(Player player) {
        int attackScore = 0;
        for (Move move : player.getLegalMoves()) {
            if (move.isAttack()) {
                Piece movedPiece = move.getMovedPiece();
                Piece attackedPiece = move.getAttackedPiece();
                if (movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static int pieceEvaluations(Player player) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for (Piece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if (piece.getPieceType().isBishop()) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private static int mobility(Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int mobilityRatio(Player player) {
        return (int) ((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int kingThreats(Player player,
                                   int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : check(player);
    }

    private static int check(Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castle(Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int pawnStructure(Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety(Player player) {
        PieceDistance kingDistance = KingSafetyAnalyzer.get().calculateKingTropism(player);
        return ((kingDistance.getEnemyPiece().getPieceValue() / 100) * kingDistance.getDistance());
    }

    private static int rookStructure(Board board, Player player) {
        return RookStructureAnalyzer.get().rookStructureScore(board, player);
    }

}
