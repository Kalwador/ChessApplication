package com.chess.spring.repositories;

import com.chess.spring.entities.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangelogRepository extends JpaRepository<Backlog, Long> {
}
