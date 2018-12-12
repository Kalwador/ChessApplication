package com.chess.spring.repositories;

import com.chess.spring.entities.register.RegisterAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterAttemptRepository extends JpaRepository<RegisterAttempt, Long> {
    boolean existsByUid(String uid);
}
