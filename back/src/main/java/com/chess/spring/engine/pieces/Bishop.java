package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.simple.MajorAttackMove;
import com.chess.spring.engine.move.simple.MajorMove;
import com.chess.spring.engine.move.simple.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class Bishop extends AbstractPiece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop( PieceColor pieceColor,
                   int piecePosition) {
         super(PieceType.BISHOP, pieceColor, piecePosition, true);
    }

    public Bishop( PieceColor pieceColor,
                   int piecePosition,
                    boolean isFirstMove) {
        super(PieceType.BISHOP, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves( Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = getPiecePosition();
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                    isEighthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                     AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                    else {
                         PieceColor piecePieceColor = pieceAtDestination.getPieceAllegiance();
                        if (getPieceColor() != piecePieceColor) {
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public int locationBonus() {
        return getPieceColor().bishopBonus(getPiecePosition());
    }

    @Override
    public Bishop movePiece( Move move) {
        return PieceUtils.INSTANCE.getMovedBishop(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getPieceType().toString();
    }

    private static boolean isFirstColumnExclusion( int currentCandidate,
                                                   int candidateDestinationCoordinate) {
        return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) &&
                ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnExclusion( int currentCandidate,
                                                    int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) &&
                        ((currentCandidate == -7) || (currentCandidate == 9));
    }

}