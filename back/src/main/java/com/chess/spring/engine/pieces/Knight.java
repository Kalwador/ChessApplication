package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.engine.moves.simple.MoveImpl;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.engine.pieces.utils.PieceType;
import com.chess.spring.engine.pieces.utils.PieceService;

import java.util.ArrayList;
import java.util.Collection;
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
    public Collection<AbstractMove> getOptionalMoves(Board board) {
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
        return BoardService.INSTANCE.FIRST_COLUMN.get(currentPosition) && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardService.INSTANCE.SECOND_COLUMN.get(currentPosition) && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion( int currentPosition,
                                                     int candidateOffset) {
        return BoardService.INSTANCE.SEVENTH_COLUMN.get(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardService.INSTANCE.EIGHTH_COLUMN.get(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }

}