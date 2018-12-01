package com.chess.spring.repositories;

import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePvPRepository extends JpaRepository<GamePvP, Long> {
//    @Query("from GamePvP where status = :status and ((whitePlayer is null and blackPlayer <> :account) or (blackPlayer is null and whitePlayer <> :account)) order by id")
//    Page<GamePvP> findEmptyRoom(Pageable pageable, @Param(value = "status") GamePvPStatus status, @Param(value = "account") Account account);
}
