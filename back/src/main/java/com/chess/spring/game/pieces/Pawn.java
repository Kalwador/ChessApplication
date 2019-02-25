package com.chess.spring.game.pieces;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.simple.pawn.*;
import com.chess.spring.game.pieces.utils.PieceService;
import com.chess.spring.game.pieces.utils.PieceType;
import com.chess.spring.game.pieces.utils.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Pawn
        extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {8, 16, 7, 9};

    public Pawn(PlayerColor allegiance,
                int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn(PlayerColor playerColor,
                int piecePosition,
                boolean isFirstMove) {
        super(PieceType.PAWN, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return getColor().pawnBonus(getPosition());
    }

    @Override
    public List<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            int candidateDestinationCoordinate =
                    getPosition() + (getColor().getDirection() * currentCandidateOffset);
            if (!BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnAbstractMove(board, this, candidateDestinationCoordinate), PieceService.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnAbstractMove(board, this, candidateDestinationCoordinate), PieceService.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnAbstractMove(board, this, candidateDestinationCoordinate), PieceService.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnAbstractMove(board, this, candidateDestinationCoordinate), PieceService.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                } else {
                    legalMoves.add(new PawnAbstractMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardConfiguration.getInstance().SECOND_ROW.get(getPosition()) && getColor().isBlack()) ||
                            (BoardConfiguration.getInstance().SEVENTH_ROW.get(getPosition()) && getColor().isWhite()))) {
                int behindCandidateDestinationCoordinate =
                        getPosition() + (getColor().getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                        board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardConfiguration.getInstance().EIGHTH_COLUMN.get(getPosition()) && getColor().isWhite()) ||
                            (BoardConfiguration.getInstance().FIRST_COLUMN.get(getPosition()) && getColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    AbstractPiece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceService.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceService.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceService.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PieceService.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getPassingAttack() != null && board.getPassingAttack().getPosition() ==
                        (getPosition() + (getColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getPassingAttack();
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnPassingAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardConfiguration.getInstance().FIRST_COLUMN.get(getPosition()) && getColor().isWhite()) ||
                            (BoardConfiguration.getInstance().EIGHTH_COLUMN.get(getPosition()) && getColor().isBlack()))) {
                if (board.getPiece(candidateDestinationCoordinate) != null) {
                    if (getColor() !=
                            board.getPiece(candidateDestinationCoordinate).getPieceAllegiance()) {
                        if (getColor().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceService.INSTANCE.getMovedQueen(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceService.INSTANCE.getMovedRook(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceService.INSTANCE.getMovedBishop(getColor(), candidateDestinationCoordinate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)), PieceService.INSTANCE.getMovedKnight(getColor(), candidateDestinationCoordinate)));
                        } else {
                            legalMoves.add(
                                    new PawnAttackAbstractMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)));
                        }
                    }
                } else if (board.getPassingAttack() != null && board.getPassingAttack().getPosition() ==
                        (getPosition() - (getColor().getOppositeDirection()))) {
                    AbstractPiece pieceOnCandidate = board.getPassingAttack();
                    if (getColor() != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnPassingAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

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
    public Pawn movePiece(AbstractMove move) {
        return PieceService.INSTANCE.getMovedPawn(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    public static int[] WHITE_BONUS_COORDINATES = {
            0, 0, 0, 0, 0, 0, 0, 0,
            75, 75, 75, 75, 75, 75, 75, 75,
            25, 25, 29, 29, 29, 29, 25, 25,
            5, 5, 10, 25, 25, 10, 5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, -5, -10, 0, 0, -10, -5, 5,
            5, 10, 10, -20, -20, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    public static int[] BLACK_BONUS_COORDINATES = {
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, -20, -20, 10, 10, 5,
            5, -5, -10, 0, 0, -10, -5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, 5, 10, 25, 25, 10, 5, 5,
            25, 25, 29, 29, 29, 29, 25, 25,
            75, 75, 75, 75, 75, 75, 75, 75,
            0, 0, 0, 0, 0, 0, 0, 0
    };

}