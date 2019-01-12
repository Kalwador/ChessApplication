package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.engine.moves.simple.MoveImpl;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.engine.pieces.utils.PieceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class King extends AbstractPiece {

    private static int[] DEFAULT_STRATEGY = {-9, -8, -7, -1, 1, 7, 8, 9};
    private boolean isCastled;
    private boolean kingSideCastleCapable;
    private boolean queenSideCastleCapable;

    public King(PlayerColor playerColor,
                int piecePosition,
                boolean kingSideCastleCapable,
                boolean queenSideCastleCapable) {
        super(PieceType.KING, playerColor, piecePosition, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(PlayerColor playerColor,
                int piecePosition,
                boolean isFirstMove,
                boolean isCastled,
                boolean kingSideCastleCapable,
                boolean queenSideCastleCapable) {
        super(PieceType.KING, playerColor, piecePosition, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }


    @Override
    public List<AbstractMove> getOptionalMoves(Board board) {
        List<AbstractMove> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : DEFAULT_STRATEGY) {
            if (isFirstColumnExclusion(getPosition(), currentCandidateOffset) ||
                    isEighthColumnExclusion(getPosition(), currentCandidateOffset)) {
                continue;
            }
            int candidateDestinationCoordinate = getPosition() + currentCandidateOffset;
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
    public int locationBonus() {
        return getColor().kingBonus(getPosition());
    }

    @Override
    public King movePiece(AbstractMove move) {
        return new King(getColor(), move.getDestination(), false, move.isCastlingMove(), false, false);
    }


    private static boolean isFirstColumnExclusion(int currentCandidate,
                                                  int candidateDestinationCoordinate) {
        return BoardService.INSTANCE.FIRST_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
                (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion(int currentCandidate,
                                                   int candidateDestinationCoordinate) {
        return BoardService.INSTANCE.EIGHTH_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
                (candidateDestinationCoordinate == 9));
    }

    public static int[] WHITE_BONUS_COORDINATES = {
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            20, 20, 0, 0, 0, 0, 20, 20,
            20, 30, 10, 0, 0, 10, 30, 20
    };

    public static int[] BLACK_BONUS_COORDINATES = {
            20, 30, 10, 0, 0, 10, 30, 20,
            20, 20, 0, 0, 0, 0, 20, 20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30
    };
}