package com.chess.spring.repositories;

import com.chess.spring.entities.game.GamePvE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePvERepository extends JpaRepository<GamePvE, Long>{
//    Page<GamePvE> findByAccount(Pageable page, Account );
}
