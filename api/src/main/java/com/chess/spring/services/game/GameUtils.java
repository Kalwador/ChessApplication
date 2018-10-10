package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.engine.classic.board.MoveTransition;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.utils.pgn.FenUtilities;

import java.util.Random;

public abstract class GameUtils {

    /**
     * Draw a random color
     * @return random player color
     */
    protected PlayerColor drawColor(){
        return new Random().nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    public Board executeMove(String fenBoard, MoveDTO moveDTO) {
        Board board = FenUtilities.createGameFromFEN(fenBoard);
        final Move move = Move.MoveFactory.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
        return executeMove(board, move);
    }
    public Board executeMove(String fenBoard, Move move) {
        Board board = FenUtilities.createGameFromFEN(fenBoard);
        return executeMove(board, move);
    }
    public Board executeMove(Board board, Move move) {
        final MoveTransition transition = board.currentPlayer().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            board = transition.getToBoard();
//            moveLog.addMove(move);
        }
        return board;
    }
}
