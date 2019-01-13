package com.chess.spring.game.pieces;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.game.moves.simple.MoveImpl;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.pieces.utils.PieceType;
import com.chess.spring.game.pieces.utils.PieceService;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {-8, -1, 1, 8};

    public Rook(PlayerColor playerColor, int piecePosition) {
        super(PieceType.ROOK, playerColor, piecePosition, true);
    }

    public Rook(PlayerColor playerColor, int piecePosition, boolean isFirstMove) {
        super(PieceType.ROOK, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public List<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            int candidateDestinationCoordinate = getPosition();
            while (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MoveImpl(board, this, candidateDestinationCoordinate));
                    } else {
                        PlayerColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                        if (getColor() != pieceAtDestinationAllegiance) {
                            legalMoves.add(new AttackMoveImpl(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public int locationBonus() {
        return getColor().rookBonus(getPosition());
    }

    @Override
    public Rook movePiece(AbstractMove move) {
        return PieceService.INSTANCE.getMovedRook(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isColumnExclusion(int currentCandidate,
                                             int candidateDestinationCoordinate) {
        return (BoardConfiguration.getInstance().FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1)) ||
                (BoardConfiguration.getInstance().EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
    }

    public static int[] WHITE_PREFERRED_COORDINATES = {
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 20, 20, 20, 20, 20, 20, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
    };

    public static int[] BLACK_PREFERRED_COORDINATES = {
            0, 0, 0, 5, 5, 0, 0, 0,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            5, 20, 20, 20, 20, 20, 20, 5,
            0, 0, 0, 0, 0, 0, 0, 0,
    };

}