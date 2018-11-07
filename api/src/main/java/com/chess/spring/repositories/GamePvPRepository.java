package com.chess.spring.repositories;

import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GamePvPRepository extends JpaRepository<GamePvP, Long>{
    Optional<GamePvP> findFirstByStatus(GamePvPStatus status);
}
