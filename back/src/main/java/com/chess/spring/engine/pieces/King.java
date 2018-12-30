package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.moves.simple.MajorAttackMove;
import com.chess.spring.engine.moves.simple.MajorMove;
import com.chess.spring.engine.moves.simple.Move;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
public class King extends AbstractPiece {

    private static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
    private boolean isCastled;
    private boolean kingSideCastleCapable;
    private boolean queenSideCastleCapable;

    public King(PieceColor pieceColor,
                int piecePosition,
                boolean kingSideCastleCapable,
                boolean queenSideCastleCapable) {
        super(PieceType.KING, pieceColor, piecePosition, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(PieceColor pieceColor,
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
    public Collection<Move> getOptionalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            if (isFirstColumnExclusion(getPiecePosition(), currentCandidateOffset) ||
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
    public String toString() {
        return getPieceType().toString();
    }

    @Override
    public int locationBonus() {
        return getPieceColor().kingBonus(getPiecePosition());
    }

    @Override
    public King movePiece(Move move) {
        return new King(getPieceColor(), move.getDestination(), false, move.isCastlingMove(), false, false);
    }


    private static boolean isFirstColumnExclusion(int currentCandidate,
                                                  int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
                (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion(int currentCandidate,
                                                   int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
                (candidateDestinationCoordinate == 9));
    }
}