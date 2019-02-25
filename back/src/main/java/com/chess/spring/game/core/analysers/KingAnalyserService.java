package com.chess.spring.game.core.analysers;

import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.utils.PieceDistance;
import com.chess.spring.game.player.AbstractPlayer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@Service
public class KingAnalyserService {

    private KingAnalyserService() {
    }

    public PieceDistance calculateKingTropism(AbstractPlayer player) throws InvalidDataException {
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
                                        int enemyAttackTileId) throws InvalidDataException {

        int squareOneRank = getRank(kingTileId);
        int squareTwoRank = getRank(enemyAttackTileId);

        int squareOneFile = getFile(kingTileId);
        int squareTwoFile = getFile(enemyAttackTileId);

        int rankDistance = Math.abs(squareTwoRank - squareOneRank);
        int fileDistance = Math.abs(squareTwoFile - squareOneFile);

        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(int coordinate) throws InvalidDataException {
        BoardConfiguration boardConfiguration = BoardConfiguration.getInstance();
        if (boardConfiguration.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if (boardConfiguration.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if (boardConfiguration.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if (boardConfiguration.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if (boardConfiguration.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if (boardConfiguration.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if (boardConfiguration.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if (boardConfiguration.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new InvalidDataException(ExceptionMessages.SYSTEM_ERROR_INVALID_DATA.getInfo());
    }

    private static int getRank(int coordinate) throws InvalidDataException {
        BoardConfiguration boardConfiguration = BoardConfiguration.getInstance();
        if (boardConfiguration.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if (boardConfiguration.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if (boardConfiguration.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if (boardConfiguration.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if (boardConfiguration.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if (boardConfiguration.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if (boardConfiguration.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if (boardConfiguration.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new InvalidDataException(ExceptionMessages.SYSTEM_ERROR_INVALID_DATA.getInfo());
    }
}
