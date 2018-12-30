package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.moves.simple.pawn.*;
import com.chess.spring.engine.moves.simple.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn
        extends AbstractPiece {

    private static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(PieceColor allegiance,
                int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn(PieceColor pieceColor,
                int piecePosition,
                boolean isFirstMove) {
        super(PieceType.PAWN, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return getColor().pawnBonus(getPosition());
    }

    @Override
    public Collection<Move> getOptionalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    getPosition() + (getColor().getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                } else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(getPosition()) && getColor().isBlack()) ||
                            (BoardUtils.INSTANCE.SEVENTH_ROW.get(getPosition()) && getColor().isWhite()))) {
                int behindCandidateDestinationCoordinate =
                        getPosition() + (getColor().getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                        board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(getPosition()) && getColor().isWhite()) ||
                            (BoardUtils.INSTANCE.FIRST_COLUMN.get(getPosition()) && getColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    AbstractPiece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPosition() ==
                        (getPosition() + (getColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getEnPassantPawn();
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(getPosition()) && getColor().isWhite()) ||
                            (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(getPosition()) && getColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    if (getColor() !=
                            board.getPiece(candidateDestinationCoordinate).getPieceAllegiance()) {
                        if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPosition() ==
                        (getPosition() - (getColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getEnPassantPawn();
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    @Override
    public Pawn movePiece(Move move) {
        return PieceUtils.INSTANCE.getMovedPawn(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

}