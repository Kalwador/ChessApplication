package com.chess.spring.services.game;

import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class GameService {

    private final EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Root<GamePvP> rootPvP;
    private CriteriaQuery<GamePvP> criteriaQueryPvP;

    @Autowired
    public GameService(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    TypedQuery<GamePvP> getQuerryForEmptyRoom(Account account) {
        criteriaQueryPvP = criteriaBuilder.createQuery(GamePvP.class);
        rootPvP = criteriaQueryPvP.from(GamePvP.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(rootPvP.get("status"), GamePvPStatus.ROOM));
        Predicate emptySeat = criteriaBuilder.and(criteriaBuilder.isNull(rootPvP.get("whitePlayerImpl")), criteriaBuilder.notEqual(rootPvP.get("blackPlayerImpl"), account));
        Predicate notInGame = criteriaBuilder.and(criteriaBuilder.isNull(rootPvP.get("blackPlayerImpl")), criteriaBuilder.notEqual(rootPvP.get("whitePlayerImpl"), account));
        Predicate join = criteriaBuilder.or(emptySeat, notInGame);
        predicates.add(join);

        criteriaQueryPvP.where(predicates.toArray(new Predicate[0]));
        criteriaQueryPvP.orderBy(criteriaBuilder.asc(rootPvP.get("id")));
        return entityManager.createQuery(criteriaQueryPvP);
    }


    TypedQuery<GamePvP> getQueryForGames(Account current, List<GamePvPStatus> statuses, Pageable page) {
        criteriaQueryPvP = criteriaBuilder.createQuery(GamePvP.class);
        rootPvP = criteriaQueryPvP.from(GamePvP.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate status = rootPvP.get("status").in(statuses);
        Predicate player = criteriaBuilder.or(criteriaBuilder.equal(rootPvP.get("whitePlayerImpl"), current), criteriaBuilder.equal(rootPvP.get("blackPlayerImpl"), current));
        Predicate join = criteriaBuilder.and(status, player);
        predicates.add(join);

        criteriaQueryPvP.where(predicates.toArray(new Predicate[0]));
        criteriaQueryPvP.orderBy(criteriaBuilder.asc(page.getSort().isSorted() ? rootPvP.get(page.getSort().toString()) : rootPvP.get("id")));
        return entityManager.createQuery(criteriaQueryPvP);
    }
}
