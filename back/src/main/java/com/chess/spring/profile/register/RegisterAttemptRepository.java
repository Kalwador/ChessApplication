package com.chess.spring.profile.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterAttemptRepository extends JpaRepository<RegisterAttempt, Long> {
    boolean existsByUid(String uid);
}
