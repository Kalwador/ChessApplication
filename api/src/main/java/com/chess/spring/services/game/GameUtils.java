package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.engine.classic.board.MoveTransition;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.utils.pgn.FenUtilities;

import java.util.Random;

public abstract class GameUtils {

    /**
     * Draw a random color
     * @return random player color
     */
    protected PlayerColor drawColor(){
        return new Random(System.currentTimeMillis()).nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    public Board executeMove(String fenBoard, MoveDTO moveDTO) throws InvalidDataException {
        Board board = FenUtilities.createGameFromFEN(fenBoard);
        final Move move = Move.MoveFactory.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
        return executeMove(board, move);
    }
    public Board executeMove(String fenBoard, Move move) throws InvalidDataException {
        Board board = FenUtilities.createGameFromFEN(fenBoard);
        return executeMove(board, move);
    }
    public Board executeMove(Board board, Move move) throws InvalidDataException {
        final MoveTransition transition = board.currentPlayer().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            board = transition.getToBoard();
//            moveLog.addMove(move);
        } else {
            throw new InvalidDataException("Nie poprawny ruch");
        }
        return board;
    }
}
