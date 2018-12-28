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

public  class Queen extends AbstractPiece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1,
        7, 8, 9 };

    public Queen( PieceColor pieceColor,  int piecePosition) {
        super(PieceType.QUEEN, pieceColor, piecePosition, true);
    }

    public Queen( PieceColor pieceColor,
                  int piecePosition,
                  boolean isFirstMove) {
        super(PieceType.QUEEN, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves( Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (true) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                    isEightColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    break;
                } else {
                     AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                         PieceColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                        if (this.piecePieceColor != pieceAtDestinationAllegiance) {
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
        return this.piecePieceColor.queenBonus(this.piecePosition);
    }

    @Override
    public Queen movePiece( Move move) {
        return PieceUtils.INSTANCE.getMovedQueen(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion( int currentPosition,
                                                   int candidatePosition) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(candidatePosition) && ((currentPosition == -9)
                || (currentPosition == -1) || (currentPosition == 7));
    }

    private static boolean isEightColumnExclusion( int currentPosition,
                                                   int candidatePosition) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidatePosition) && ((currentPosition == -7)
                || (currentPosition == 1) || (currentPosition == 9));
    }

}