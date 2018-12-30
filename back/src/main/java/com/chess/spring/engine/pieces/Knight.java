package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.moves.simple.MajorAttackMove;
import com.chess.spring.engine.moves.simple.MajorMove;
import com.chess.spring.engine.moves.simple.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class Knight extends AbstractPiece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight( PieceColor pieceColor,
                   int piecePosition) {
        super(PieceType.KNIGHT, pieceColor, piecePosition, true);
    }

    public Knight( PieceColor pieceColor,
                   int piecePosition,
                   boolean isFirstMove) {
        super(PieceType.KNIGHT, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> getOptionalMoves(Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            if(isFirstColumnExclusion(getPiecePosition(), currentCandidateOffset) ||
               isSecondColumnExclusion(getPiecePosition(), currentCandidateOffset) ||
               isSeventhColumnExclusion(getPiecePosition(), currentCandidateOffset) ||
               isEighthColumnExclusion(getPiecePosition(), currentCandidateOffset)) {
                continue;
            }
             int candidateDestinationCoordinate = getPiecePosition() + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                 AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                     PieceColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                    if (getPieceColor() != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public int locationBonus() {
        return getPieceColor().knightBonus(getPiecePosition());
    }

    @Override
    public Knight movePiece( Move move) {
        return PieceUtils.INSTANCE.getMovedKnight(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getPieceType().toString();
    }

    private static boolean isFirstColumnExclusion( int currentPosition,
                                                   int candidateOffset) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(currentPosition) && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardUtils.INSTANCE.SECOND_COLUMN.get(currentPosition) && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion( int currentPosition,
                                                     int candidateOffset) {
        return BoardUtils.INSTANCE.SEVENTH_COLUMN.get(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion( int currentPosition,
                                                    int candidateOffset) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }

}