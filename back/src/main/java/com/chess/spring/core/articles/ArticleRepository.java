package com.chess.spring.core.articles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long > {

}
