package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.move.Move.MajorAttackMove;
import com.chess.spring.engine.move.Move.MajorMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class King extends Piece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
    private  boolean isCastled;
    private  boolean kingSideCastleCapable;
    private  boolean queenSideCastleCapable;

    public King( PieceColor pieceColor,
                 int piecePosition,
                 boolean kingSideCastleCapable,
                 boolean queenSideCastleCapable) {
        super(PieceType.KING, pieceColor, piecePosition, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King( PieceColor pieceColor,
                 int piecePosition,
                 boolean isFirstMove,
                 boolean isCastled,
                 boolean kingSideCastleCapable,
                 boolean queenSideCastleCapable) {
        super(PieceType.KING, pieceColor, piecePosition, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }

    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable;
    }

    @Override
    public Collection<Move> calculateLegalMoves( Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
             int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                 Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                     PieceColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                    if (this.piecePieceColor != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public int locationBonus() {
        return this.piecePieceColor.kingBonus(this.piecePosition);
    }

    @Override
    public King movePiece( Move move) {
        return new King(this.piecePieceColor, move.getDestinationCoordinate(), false, move.isCastlingMove(), false, false);
    }

    @Override
    public boolean equals( Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
         King king = (King) other;
        return isCastled == king.isCastled;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + (isCastled ? 1 : 0);
    }

    private static boolean isFirstColumnExclusion( int currentCandidate,
                                                   int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
                (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion( int currentCandidate,
                                                    int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
                (candidateDestinationCoordinate == 9));
    }
}