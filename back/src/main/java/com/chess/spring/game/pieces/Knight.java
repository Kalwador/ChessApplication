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

public  class Knight extends AbstractPiece {

    private  static int[] DEFAULT_STRATEGY = { -17, -15, -10, -6, 6, 10, 15, 17 };


    public Knight( PlayerColor playerColor,
                   int piecePosition) {
        super(PieceType.KNIGHT, playerColor, piecePosition, true);
    }

    public Knight( PlayerColor playerColor,
                   int piecePosition,
                   boolean isFirstMove) {
        super(PieceType.KNIGHT, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public List<AbstractMove> getOptionalMoves(Board board) {
         List<AbstractMove> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : DEFAULT_STRATEGY) {
            if(isFirstColumnExclusion(getPosition(), currentCandidateOffset) ||
               isSecondColumnExclusion(getPosition(), currentCandidateOffset) ||
               isSeventhColumnExclusion(getPosition(), currentCandidateOffset) ||
               isEighthColumnExclusion(getPosition(), currentCandidateOffset)) {
                continue;
            }
             int candidateDestinationCoordinate = getPosition() + currentCandidateOffset;
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
                }
            }
        }
        return legalMoves;
    }

    @Override
    public int locationBonus() {
        return getColor().knightBonus(getPosition());
    }

    @Override
    public Knight movePiece( AbstractMove move) {
        return PieceService.INSTANCE.getMovedKnight(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isFirstColumnExclusion( int currentPosition,
                                                   int candidateOffset) {
        return BoardConfiguration.getInstance().FIRST_COLUMN.get(currentPosition) && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardConfiguration.getInstance().SECOND_COLUMN.get(currentPosition) && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion( int currentPosition,
                                                     int candidateOffset) {
        return BoardConfiguration.getInstance().SEVENTH_COLUMN.get(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardConfiguration.getInstance().EIGHTH_COLUMN.get(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }

    public static int[] WHITE_BONUS_COORDINATES = {
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
    };

    public static int[] BLACK_BONUS_COORDINATES = {
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50,
    };

}