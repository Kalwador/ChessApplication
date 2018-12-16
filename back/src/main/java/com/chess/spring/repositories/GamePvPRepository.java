package com.chess.spring.repositories;

import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamePvPRepository extends JpaRepository<GamePvP, Long> {
    Page<GamePvP> findByStatusNotIn(Pageable page, List<GamePvPStatus> statuses);

    @Query("select g.board from GamePvP g where g.id = :gameId")
    Optional<String> getBoardByGameId(@Param(value = "gameId") Long gameId);
}
