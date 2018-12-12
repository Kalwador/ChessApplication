package com.chess.spring.repositories;

import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.models.game.GamePvEStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamePvERepository extends JpaRepository<GamePvE, Long>{
    Page<GamePvE> findByStatusNotIn(Pageable page, List<GamePvEStatus> statuses);

    @Query("select g.board from GamePvE g where g.id = :gameId")
    Optional<String> getBoardByGameId(@Param(value = "gameId") Long gameId);
}
