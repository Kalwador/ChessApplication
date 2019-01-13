package com.chess.spring.game.board;

import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.ErrorMove;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.Transition;
import com.chess.spring.game.pieces.*;
import com.chess.spring.game.pieces.utils.PieceType;
import java.util.*;


public enum BoardService {
    INSTANCE;
    public final List<String> ALGEBRAIC_NOTATION = BoardUtils.initializeAlgebraicNotation();

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < BoardConfiguration.TILES_MAX;
    }

    public String getPositionAtCoordinate(int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    public static boolean kingThreat( AbstractMove move) {
        Board board = move.getBoard();
        Transition transition = board.getCurrentPlayer().makeMove(move);
        return transition.getAfterMoveBoard().getCurrentPlayer().isInCheck();
    }

    public static boolean isKingPawnTrap(Board board,
                                         King king,
                                         int frontTile) {
        AbstractPiece piece = board.getPiece(frontTile);
        return piece != null && piece.getType().isPawn() &&
                piece.getPieceAllegiance() != king.getPieceAllegiance();
    }

    public static int calculatePieceExchangeValue(AbstractMove move) {
        AbstractPiece movingPiece = move.getPiece();
        if (move.isAttackMove()) {
             AbstractPiece attackedPiece = move.getAttackedPiece();
            return (attackedPiece.getPieceValue() - movingPiece.getPieceValue() + PieceType.KING.getPieceValue()) * 100;
        }
        return PieceType.KING.getPieceValue() - movingPiece.getPieceValue();
    }

    public static List<AbstractMove> lastNMoves(Board board, int N) {
        List<AbstractMove> moveHistory = new ArrayList<>();
        AbstractMove move = board.getTransition();
        int i = 0;
        while (move != ErrorMove.getInstance() && i < N) {
            moveHistory.add(move);
            move = move.getBoard().getTransition();
            i++;
        }
        return moveHistory;
    }

    public static boolean isEndGame(Board board) {
        return board.getCurrentPlayer().isInCheckMate() ||
                board.getCurrentPlayer().isInStaleMate();
    }
}
