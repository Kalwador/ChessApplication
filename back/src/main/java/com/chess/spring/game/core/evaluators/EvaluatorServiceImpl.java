package com.chess.spring.game.core.evaluators;

import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.core.analysers.KingAnalyserService;
import com.chess.spring.game.core.analysers.PawnAnalyserService;
import com.chess.spring.game.core.analysers.RookAnalyserService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.utils.PieceDistance;
import com.chess.spring.game.player.AbstractPlayer;
import org.springframework.stereotype.Service;

import static com.chess.spring.game.core.evaluators.BonusPoints.*;

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
    public int evaluate(Board board, int level) throws InvalidDataException {
        return score(board, board.whitePlayer(), level) - score(board, board.blackPlayer(), level);
    }

    private int score(Board board, AbstractPlayer player, int level) throws InvalidDataException {
        return mobility(player) +
                kingThreats(player, level) +
                attacks(player) + castle(player) +
                pieceEvaluations(player) +
                pawnStructure(player) +
                kingSafety(player)
                + rookStructure(board, player);
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
        return attackScore * ATTACK;
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
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS : 0);
    }

    private int mobility(AbstractPlayer player) {
        return MOBILITY * mobilityRatio(player);
    }

    private int mobilityRatio(AbstractPlayer player) {
        return (int) ((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
    }

    private int kingThreats(AbstractPlayer player, int level) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE * levelBonus(level) : check(player);
    }

    private int check(AbstractPlayer player) {
        return player.getOpponent().isInCheck() ? CHECK : 0;
    }

    private int levelBonus(int level) {
        return level == 0 ? 1 : 100 * level;
    }

    private int castle(AbstractPlayer player) {
        return player.isCastled() ? CASTLE : 0;
    }

    private int pawnStructure(AbstractPlayer player) {
        return pawnAnalyserService.pawnStructureScore(player);
    }

    private int kingSafety(AbstractPlayer player) throws InvalidDataException {
        PieceDistance pieceDistance = kingAnalyserService.calculateKingTropism(player);
        return ((pieceDistance.getEnemyPiece().getPieceValue() / 100) * pieceDistance.getDistance());
    }

    private int rookStructure(Board board, AbstractPlayer player) {
        return rookAnalyserService.rookStructureScore(board, player);
    }

}
