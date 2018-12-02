package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.engine.classic.board.MoveTransition;
import com.chess.spring.engine.classic.player.ai.StockAlphaBeta;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.models.game.GameEndStatus;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.utils.pgn.FenUtilities;

import java.util.Random;

public abstract class GameUtils {

    /**
     * Draw a random color
     *
     * @return random player color
     */
    PlayerColor drawColor() {
        return new Random(System.currentTimeMillis()).nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    protected Board map(String fenBoard) {
        return FenUtilities.createGameFromFEN(fenBoard);
    }

    protected Move map(Board board, MoveDTO moveDTO) {
        return Move.MoveFactory.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
    }

    Board executeMove(String fenBoard, MoveDTO moveDTO) throws InvalidDataException {
        Board board = map(fenBoard);
        final Move move = map(board, moveDTO);
        return executeMove(board, move);
    }

    protected Board executeMove(String fenBoard, Move move) throws InvalidDataException {
        Board board = map(fenBoard);
        return executeMove(board, move);
    }

    Board executeMove(Board board, Move move) throws InvalidDataException {
        final MoveTransition transition = board.currentPlayer().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            board = transition.getToBoard();
//            moveLog.addMove(move);
        } else {
            throw new InvalidDataException("Nie poprawny ruch");
        }
        return board;
    }

    GameEndStatus checkEndOfGame(Board board) {
        if (board.currentPlayer().isInCheckMate()) {
            return GameEndStatus.CHECKMATE;
        }
        if (board.currentPlayer().isInStaleMate()) {
            return GameEndStatus.STALE_MATE;
        }
        return null;
    }

    Move getBestMove(Board board, int level) {
        final StockAlphaBeta strategy = new StockAlphaBeta(level);
//        strategy.addObserver(Table.get().getDebugPanel());
        return strategy.execute(board);
    }
}
