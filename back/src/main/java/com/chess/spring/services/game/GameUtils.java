package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.Move;
import com.chess.spring.engine.moves.MoveFactory;
import com.chess.spring.engine.moves.Transition;
import com.chess.spring.engine.classic.player.ai.algorithms.AlphaBetaAlgorithm;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.models.game.GameEndStatus;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.utils.pgn.FenUtilities;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@Service
public class GameUtils {

    private AlphaBetaAlgorithm alphaBetaAlgorithm;

    @Autowired
    public GameUtils(AlphaBetaAlgorithm alphaBetaAlgorithm) {
        this.alphaBetaAlgorithm = alphaBetaAlgorithm;
    }

    PlayerColor drawColor() {
        return new Random(System.currentTimeMillis()).nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    protected Board map(String fenBoard) {
        return FenUtilities.createGameFromFEN(fenBoard);
    }

    protected Move map(Board board, MoveDTO moveDTO) {
        return MoveFactory.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
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
        final Transition transition = board.currentPlayer().makeMove(move);
        if (transition.getStatus().isDone()) {
            board = transition.getAfterMoveBoard();
//            moveLog.addMove(moves);
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
        return alphaBetaAlgorithm.execute(board, level);
    }

}
