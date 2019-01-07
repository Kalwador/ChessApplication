package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.moves.MoveService;
import com.chess.spring.engine.moves.Transition;
import com.chess.spring.engine.core.algorithms.AlphaBetaAlgorithm;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.models.events.GameEndType;
import com.chess.spring.models.events.UpdateStatisticsEvent;
import com.chess.spring.models.game.GameEndStatus;
import com.chess.spring.models.game.GameType;
import com.chess.spring.utils.pgn.FenUtilities;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Data
@Service
public class GameUtils {

    private AlphaBetaAlgorithm alphaBetaAlgorithm;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public GameUtils(
            AlphaBetaAlgorithm alphaBetaAlgorithm,
            ApplicationEventPublisher applicationEventPublisher) {
        this.alphaBetaAlgorithm = alphaBetaAlgorithm;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    PlayerColor drawColor() {
        return new Random(System.currentTimeMillis()).nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    protected Board map(String fenBoard) {
        return FenUtilities.createGameFromFEN(fenBoard);
    }

    protected AbstractMove map(Board board, MoveDTO moveDTO) {
        return MoveService.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
    }

    Board executeMove(String fenBoard, MoveDTO moveDTO) throws InvalidDataException {
        Board board = map(fenBoard);
        final AbstractMove move = map(board, moveDTO);
        return executeMove(board, move);
    }

    protected Board executeMove(String fenBoard, AbstractMove move) throws InvalidDataException {
        Board board = map(fenBoard);
        return executeMove(board, move);
    }

    Board executeMove(Board board, AbstractMove move) throws InvalidDataException {
        final Transition transition = board.getCurrentPlayer().makeMove(move);
        if (transition.getStatus().isDone()) {
            board = transition.getAfterMoveBoard();
//            moveLog.addMove(moves);
        } else {
            throw new InvalidDataException(ExceptionMessages.GAME_INVALID_MOVE.getInfo());
        }
        return board;
    }

    GameEndStatus checkEndOfGame(Board board) {
        if (board.getCurrentPlayer().isInCheckMate()) {
            return GameEndStatus.CHECKMATE;
        }
        if (board.getCurrentPlayer().isInStaleMate()) {
            return GameEndStatus.STALE_MATE;
        }
        return null;
    }

    AbstractMove getBestMove(Board board, int level) {
        return alphaBetaAlgorithm.execute(board, level);
    }

    void updateStatistics(Long playerId, GameType gameType, GameEndType gameEndType, Integer enemyRank){
        UpdateStatisticsEvent updateStatisticsEvent = new UpdateStatisticsEvent(this, playerId, gameType, gameEndType, enemyRank);
        log.trace("Update statistic: " + updateStatisticsEvent.toString());
        this.applicationEventPublisher.publishEvent(updateStatisticsEvent);
    }

}
