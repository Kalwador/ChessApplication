package com.chess.spring.game;

import com.chess.spring.communication.event.UpdateStatisticsEvent;
import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.core.algorithms.AbstractAlgorithm;
import com.chess.spring.game.core.algorithms.AlphaBetaAlgorithm;
import com.chess.spring.game.moves.ErrorMove;
import com.chess.spring.game.moves.MoveDTO;
import com.chess.spring.game.moves.Transition;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.game.pvp.GamePvPStatus;
import com.chess.spring.profile.account.Account;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Data
@Service
public class GameService {
    private AbstractAlgorithm algorithm;
    private ApplicationEventPublisher applicationEventPublisher;
    public static int UP_DIRECTION = -1;
    public static int DOWN_DIRECTION = 1;
    private final EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Root<GamePvP> rootPvP;
    private CriteriaQuery<GamePvP> criteriaQueryPvP;

    @Autowired
    public GameService(EntityManager entityManager,
                       AlphaBetaAlgorithm alphaBetaAlgorithm,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        this.algorithm = alphaBetaAlgorithm;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public TypedQuery<GamePvP> getQuerryForEmptyRoom(Account account) {
        criteriaQueryPvP = criteriaBuilder.createQuery(GamePvP.class);
        rootPvP = criteriaQueryPvP.from(GamePvP.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(rootPvP.get("status"), GamePvPStatus.ROOM));
        Predicate emptySeat = criteriaBuilder.and(
                criteriaBuilder.isNull(rootPvP.get("whitePlayer")),
                criteriaBuilder.notEqual(rootPvP.get("blackPlayer"), account));
        Predicate notInGame = criteriaBuilder.and(
                criteriaBuilder.isNull(rootPvP.get("blackPlayer")),
                criteriaBuilder.notEqual(rootPvP.get("whitePlayer"), account));
        Predicate join = criteriaBuilder.or(emptySeat, notInGame);
        predicates.add(join);

        criteriaQueryPvP.where(predicates.toArray(new Predicate[0]));
        criteriaQueryPvP.orderBy(criteriaBuilder.asc(rootPvP.get("id")));
        return entityManager.createQuery(criteriaQueryPvP);
    }


    public TypedQuery<GamePvP> getQueryForGames(Account current, List<GamePvPStatus> statuses, Pageable page) {
        criteriaQueryPvP = criteriaBuilder.createQuery(GamePvP.class);
        rootPvP = criteriaQueryPvP.from(GamePvP.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate status = rootPvP.get("status").in(statuses);
        Predicate player = criteriaBuilder.or(criteriaBuilder.equal(rootPvP.get("whitePlayer"), current), criteriaBuilder.equal(rootPvP.get("blackPlayer"), current));
        Predicate join = criteriaBuilder.and(status, player);
        predicates.add(join);

        criteriaQueryPvP.where(predicates.toArray(new Predicate[0]));
        criteriaQueryPvP.orderBy(criteriaBuilder.asc(page.getSort().isSorted() ? rootPvP.get(page.getSort().toString()) : rootPvP.get("id")));
        return entityManager.createQuery(criteriaQueryPvP);
    }


    public PlayerColor drawColor() {
        return new Random(System.currentTimeMillis()).nextInt() > 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    public Board map(String fenBoard) throws InvalidDataException {
        return FenService.parse(fenBoard);
    }

    public AbstractMove map(Board board, MoveDTO moveDTO) {
        return createMove(board, moveDTO.getSource(), moveDTO.getDestination());
    }

    public Board executeMove(String fenBoard, MoveDTO moveDTO) throws InvalidDataException {
        Board board = map(fenBoard);
        final AbstractMove move = map(board, moveDTO);
        return executeMove(board, move);
    }

    public Board executeMove(String fenBoard, AbstractMove move) throws InvalidDataException {
        Board board = map(fenBoard);
        return executeMove(board, move);
    }

    public Board executeMove(Board board, AbstractMove move) throws InvalidDataException {
        final Transition transition = board.getCurrentPlayer().makeMove(move);
        if (transition.getStatus().isDone()) {
            board = transition.getAfterMoveBoard();
        } else {
            throw new InvalidDataException(ExceptionMessages.GAME_INVALID_MOVE.getInfo());
        }
        return board;
    }

    public GameEndStatus checkEndOfGame(Board board) {
        if (board.getCurrentPlayer().isInCheckMate()) {
            return GameEndStatus.CHECKMATE;
        }
        if (board.getCurrentPlayer().isInStaleMate()) {
            return GameEndStatus.STALE_MATE;
        }
        return null;
    }

    public AbstractMove getBestMove(Board board, int level) throws InvalidDataException {
        return algorithm.getBestMove(board, level);
    }

    public void updateStatistics(Long playerId, GameType gameType, GameEndType gameEndType, Integer enemyRank) {
        UpdateStatisticsEvent updateStatisticsEvent = new UpdateStatisticsEvent(this, playerId, gameType, gameEndType, enemyRank);
        log.trace("Update statistic: " + updateStatisticsEvent.toString());
        this.applicationEventPublisher.publishEvent(updateStatisticsEvent);
    }

    public AbstractMove createMove(Board board, int currentCoordinate, int destinationCoordinate) {
        for (AbstractMove move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestination() == destinationCoordinate) {
                return move;
            }
        }
        return ErrorMove.getInstance();
    }
}
