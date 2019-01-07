package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.engine.moves.simple.MoveImpl;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.engine.pieces.utils.PieceType;
import com.chess.spring.engine.pieces.utils.PieceService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {-8, -1, 1, 8};

    public Rook(PlayerColor playerColor, int piecePosition) {
        super(PieceType.ROOK, playerColor, piecePosition, true);
    }

    public Rook(PlayerColor playerColor, int piecePosition, boolean isFirstMove) {
        super(PieceType.ROOK, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            int candidateDestinationCoordinate = getPosition();
            while (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MoveImpl(board, this, candidateDestinationCoordinate));
                    } else {
                        PlayerColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                        if (getColor() != pieceAtDestinationAllegiance) {
                            legalMoves.add(new AttackMoveImpl(board, this, candidateDestinationCoordinate,
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
    public Rook movePiece(AbstractMove move) {
        return PieceService.INSTANCE.getMovedRook(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isColumnExclusion(int currentCandidate,
                                             int candidateDestinationCoordinate) {
        return (BoardService.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1)) ||
                (BoardService.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
    }

}