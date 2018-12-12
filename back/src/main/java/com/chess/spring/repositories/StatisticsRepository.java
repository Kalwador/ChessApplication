package com.chess.spring.repositories;

import com.chess.spring.entities.account.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository  extends JpaRepository<Statistics, Long> {

}