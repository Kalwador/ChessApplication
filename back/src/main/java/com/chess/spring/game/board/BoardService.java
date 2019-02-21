package com.chess.spring.game.board;

import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.ErrorMove;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.Transition;
import com.chess.spring.game.pieces.*;
import com.chess.spring.game.pieces.utils.PieceType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService {
    public static List<String> chessNotation;

    public BoardService() {
        this.chessNotation = Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        );
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < BoardConfiguration.TILES_MAX;
    }

    public static String getPositionAtCoordinate(int coordinate) {
        return chessNotation.get(coordinate);
    }

    public static boolean kingThreat(AbstractMove move) {
        Board board = move.getBoard();
        Transition transition = board.getCurrentPlayer().makeMove(move);
        return transition.getAfterMoveBoard().getCurrentPlayer().isInCheck();
    }

    public static boolean isKingPawnTrap(Board board, King king, int frontTile) {
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
        List<AbstractMove> lastMoves = new ArrayList<>();
        AbstractMove move = board.getTransition();
        int i = 0;
        while (move != ErrorMove.getInstance() && i < N) {
            lastMoves.add(move);
            move = move.getBoard().getTransition();
            i++;
        }
        return lastMoves;
    }

    public static boolean isEndGame(Board board) {
        return board.getCurrentPlayer().isInCheckMate() ||
                board.getCurrentPlayer().isInStaleMate();
    }
}
