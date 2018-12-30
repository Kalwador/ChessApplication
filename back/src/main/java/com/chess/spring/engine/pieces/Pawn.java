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
        return getPieceColor().pawnBonus(getPiecePosition());
    }

    @Override
    public Collection<Move> getOptionalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    getPiecePosition() + (getPieceColor().getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if (getPieceColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedQueen(getPieceColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedRook(getPieceColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedBishop(getPieceColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedKnight(getPieceColor(), candidateDestinationCoordinate)));
                } else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(getPiecePosition()) && getPieceColor().isBlack()) ||
                            (BoardUtils.INSTANCE.SEVENTH_ROW.get(getPiecePosition()) && getPieceColor().isWhite()))) {
                int behindCandidateDestinationCoordinate =
                        getPiecePosition() + (getPieceColor().getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                        board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(getPiecePosition()) && getPieceColor().isWhite()) ||
                            (BoardUtils.INSTANCE.FIRST_COLUMN.get(getPiecePosition()) && getPieceColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    AbstractPiece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (getPieceColor() != pieceOnCandidate.getPieceAllegiance()) {
                        if (getPieceColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedQueen(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedRook(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedBishop(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedKnight(getPieceColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (getPiecePosition() + (getPieceColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getEnPassantPawn();
                    if (getPieceColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(getPiecePosition()) && getPieceColor().isWhite()) ||
                            (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(getPiecePosition()) && getPieceColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    if (getPieceColor() !=
                            board.getPiece(candidateDestinationCoordinate).getPieceAllegiance()) {
                        if (getPieceColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedQueen(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedRook(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedBishop(getPieceColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedKnight(getPieceColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (getPiecePosition() - (getPieceColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getEnPassantPawn();
                    if (getPieceColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

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
    public Pawn movePiece(Move move) {
        return PieceUtils.INSTANCE.getMovedPawn(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

}