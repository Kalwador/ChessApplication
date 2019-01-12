package com.chess.spring.engine.core.evaluators;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.core.analysers.KingAnalyserService;
import com.chess.spring.engine.core.analysers.PawnAnalyserService;
import com.chess.spring.engine.core.analysers.RookAnalyserService;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.utils.PieceDistance;
import org.springframework.stereotype.Service;

import static com.chess.spring.engine.core.evaluators.BonusPoints.*;

@Service
public class EvaluatorServiceImpl implements EvaluatorService {

    private KingAnalyserService kingAnalyserService;
    private PawnAnalyserService pawnAnalyserService;
    private RookAnalyserService rookAnalyserService;

    private EvaluatorServiceImpl(KingAnalyserService kingAnalyserService,
                                 PawnAnalyserService pawnAnalyserService,
                                 RookAnalyserService rookAnalyserService) {
        this.kingAnalyserService = kingAnalyserService;
        this.pawnAnalyserService = pawnAnalyserService;
        this.rookAnalyserService = rookAnalyserService;
    }

    @Override
    public int evaluate(Board board,
                        int depth) {
        return score(board, board.whitePlayer(), depth) - score(board,board.blackPlayer(), depth);
    }

    private int score(Board board, AbstractPlayer player,
                      int depth) {
        return mobility(player) +
                kingThreats(player, depth) +
                attacks(player) + castle(player) +
                pieceEvaluations(player) +
                pawnStructure(player)+
                kingSafety(player)
                +rookStructure(board, player);
    }

    private static int attacks(AbstractPlayer player) {
        int attackScore = 0;
        for (AbstractMove move : player.getLegalMoves()) {
            if (move.isAttackMove()) {
                AbstractPiece movedPiece = move.getPiece();
                AbstractPiece attackedPiece = move.getAttackedPiece();
                if (movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static int pieceEvaluations(AbstractPlayer player) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for (AbstractPiece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if (piece.getType().isBishop()) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private int mobility(AbstractPlayer player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private int mobilityRatio(AbstractPlayer player) {
        return (int) ((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
    }

    private int kingThreats(AbstractPlayer player,
                            int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : check(player);
    }

    private int check(AbstractPlayer player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private int depthBonus(int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private int castle(AbstractPlayer player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private int pawnStructure(AbstractPlayer player) {
        return pawnAnalyserService.pawnStructureScore(player);
    }

    private int kingSafety(AbstractPlayer player) {
        PieceDistance pieceDistance = kingAnalyserService.calculateKingTropism(player);
        return ((pieceDistance.getEnemyPiece().getPieceValue() / 100) * pieceDistance.getDistance());
    }

    private int rookStructure(Board board, AbstractPlayer player) {
        return rookAnalyserService.rookStructureScore(board, player);
    }

}
