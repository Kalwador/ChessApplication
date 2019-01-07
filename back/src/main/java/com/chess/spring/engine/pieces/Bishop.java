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

public class Bishop extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {-9, -7, 7, 9};

    public Bishop(PlayerColor playerColor,
                  int piecePosition) {
        super(PieceType.BISHOP, playerColor, piecePosition, true);
    }

    public Bishop(PlayerColor playerColor,
                  int piecePosition,
                  boolean isFirstMove) {
        super(PieceType.BISHOP, playerColor, piecePosition, isFirstMove);
    }

    @Override
    public Collection<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            int candidateDestinationCoordinate = getPosition();
            while (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                        isEighthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MoveImpl(board, this, candidateDestinationCoordinate));
                    } else {
                        PlayerColor piecePlayerColor = pieceAtDestination.getPieceAllegiance();
                        if (getColor() != piecePlayerColor) {
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
        return getColor().bishopBonus(getPosition());
    }

    @Override
    public Bishop movePiece(AbstractMove move) {
        return PieceService.INSTANCE.getMovedBishop(move.getPiece().getPieceAllegiance(), move.getDestination());
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    private static boolean isFirstColumnExclusion(int currentCandidate,
                                                  int candidateDestinationCoordinate) {
        return (BoardService.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) &&
                ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnExclusion(int currentCandidate,
                                                   int candidateDestinationCoordinate) {
        return BoardService.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) &&
                ((currentCandidate == -7) || (currentCandidate == 9));
    }

}