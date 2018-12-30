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

public  class Rook extends AbstractPiece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = { -8, -1, 1, 8 };

    public Rook( PieceColor pieceColor,  int piecePosition) {
        super(PieceType.ROOK, pieceColor, piecePosition, true);
    }

    public Rook( PieceColor pieceColor,
                 int piecePosition,
                 boolean isFirstMove) {
        super(PieceType.ROOK, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> getOptionalMoves(Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = getPosition();
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                     AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                         PieceColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                        if (getColor() != pieceAtDestinationAllegiance) {
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
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
    public Rook movePiece( Move move) {
        return PieceUtils.INSTANCE.getMovedRook(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isColumnExclusion( int currentCandidate,
                                              int candidateDestinationCoordinate) {
        return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1)) ||
               (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
    }

}