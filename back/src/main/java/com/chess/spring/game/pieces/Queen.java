package com.chess.spring.game.pieces;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.simple.MoveImpl;
import com.chess.spring.game.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.game.pieces.utils.PieceService;
import com.chess.spring.game.pieces.utils.PieceType;
import com.chess.spring.game.pieces.utils.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(PlayerColor playerColor, int piecePosition) {
        super(PieceType.QUEEN, playerColor, piecePosition, true);
    }

    public Queen(PlayerColor playerColor,
                 int piecePosition,
                 boolean isFirstMove) {
        super(PieceType.QUEEN, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public List<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            int candidateDestinationCoordinate = getPosition();
            while (true) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                        isEightColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (!BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    break;
                } else {
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
        return getColor().queenBonus(getPosition());
    }

    @Override
    public Queen movePiece(AbstractMove move) {
        return PieceService.INSTANCE.getMovedQueen(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isFirstColumnExclusion(int currentPosition,
                                                  int candidatePosition) {
        return BoardConfiguration.getInstance().FIRST_COLUMN.get(candidatePosition) &&
                ((currentPosition == -9) || (currentPosition == -1) || (currentPosition == 7));
    }

    private static boolean isEightColumnExclusion(int currentPosition,
                                                  int candidatePosition) {
        return BoardConfiguration.getInstance().EIGHTH_COLUMN.get(candidatePosition)
                && ((currentPosition == -7) || (currentPosition == 1) || (currentPosition == 9));
    }

    public static int[] WHITE_BONUS_COORDINATES = {
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 5, 5, 5, 5, 5, 0, -10,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
    };

    public static int[] BLACK_BONUS_COORDINATES = {
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -10, 5, 5, 5, 5, 5, 0, -10,
            0, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
    };

}