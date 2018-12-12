package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.move.Move.*;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class Pawn
        extends Piece {

    private  static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn( PieceColor allegiance,
                 int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn( PieceColor pieceColor,
                 int piecePosition,
                 boolean isFirstMove) {
        super(PieceType.PAWN, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.piecePieceColor.pawnBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves( Board board) {
         List<Move> legalMoves = new ArrayList<>();
        for ( int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.piecePieceColor.getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if (this.piecePieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedQueen(this.piecePieceColor, candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedRook(this.piecePieceColor, candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedBishop(this.piecePieceColor, candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate), PieceUtils.INSTANCE.getMovedKnight(this.piecePieceColor, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.piecePieceColor.isBlack()) ||
                     (BoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition) && this.piecePieceColor.isWhite()))) {
                 int behindCandidateDestinationCoordinate =
                        this.piecePosition + (this.piecePieceColor.getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                    board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.piecePieceColor.isWhite()) ||
                      (BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.piecePieceColor.isBlack()))) {
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                     Piece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (this.piecePieceColor != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.piecePieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedQueen(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedRook(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedBishop(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceUtils.INSTANCE.getMovedKnight(this.piecePieceColor, candidateDestinationCoordinate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                           (this.piecePosition + (this.piecePieceColor.getOppositeDirection()))) {
                     Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.piecePieceColor != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            }
            else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.piecePieceColor.isWhite()) ||
                      (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.piecePieceColor.isBlack()))) {
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                    if (this.piecePieceColor !=
                            board.getPiece(candidateDestinationCoordinate).getPieceAllegiance()) {
                        if (this.piecePieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedQueen(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedRook(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedBishop(this.piecePieceColor, candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceUtils.INSTANCE.getMovedKnight(this.piecePieceColor, candidateDestinationCoordinate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.piecePieceColor.getOppositeDirection()))) {
                     Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.piecePieceColor != pieceOnCandidate.getPieceAllegiance()) {
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
        return this.pieceType.toString();
    }

    @Override
    public Pawn movePiece( Move move) {
        return PieceUtils.INSTANCE.getMovedPawn(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

}