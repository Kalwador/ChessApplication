package com.chess.spring.core.articles;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5120)
    private String photo;

    private String title;

    @Column(length = 5120)
    private String content;

    private LocalDate date;
}
