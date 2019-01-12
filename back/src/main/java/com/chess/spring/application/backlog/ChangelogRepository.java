package com.chess.spring.application.backlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangelogRepository extends JpaRepository<Backlog, Long> {
}
