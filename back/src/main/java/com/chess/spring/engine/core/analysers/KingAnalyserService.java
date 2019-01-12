package com.chess.spring.engine.core.analysers;

import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.utils.PieceDistance;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@Service
public class KingAnalyserService {

    private KingAnalyserService() {
    }

    public PieceDistance calculateKingTropism(AbstractPlayer player) {
        int playerKingSquare = player.getPlayerKing().getPosition();
        List<AbstractMove> enemyMoves = player.getOpponent().getLegalMoves();
        AbstractPiece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for (AbstractMove move : enemyMoves) {
            int currentDistance = calculateClosesDistance(playerKingSquare, move.getDestination());
            if (currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getPiece();
            }
        }
        return new PieceDistance(closestPiece, closestDistance);
    }

    private int calculateClosesDistance(int kingTileId,
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
        if (BoardService.INSTANCE.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if (BoardService.INSTANCE.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if (BoardService.INSTANCE.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if (BoardService.INSTANCE.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if (BoardService.INSTANCE.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if (BoardService.INSTANCE.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if (BoardService.INSTANCE.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if (BoardService.INSTANCE.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(int coordinate) {
        if (BoardService.INSTANCE.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if (BoardService.INSTANCE.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if (BoardService.INSTANCE.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if (BoardService.INSTANCE.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if (BoardService.INSTANCE.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if (BoardService.INSTANCE.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if (BoardService.INSTANCE.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if (BoardService.INSTANCE.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }
}
