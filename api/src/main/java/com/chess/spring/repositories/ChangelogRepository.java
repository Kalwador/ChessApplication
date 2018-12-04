package com.chess.spring.repositories;

import com.chess.spring.entities.Changelog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangelogRepository extends JpaRepository<Changelog, Long> {
}
