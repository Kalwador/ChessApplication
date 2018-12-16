package com.chess.spring.entities.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rank;

    private Integer gamesPvP;
    private Integer winGamesPvP;
    private Integer weekGamesPvP;
    private Integer weekWinGamesPvP;
    private Integer monthGamesPvP;
    private Integer monthWinGamesPvP;

    private Integer gamesPvE;
    private Integer winGamesPvE;
    private Integer weekGamesPvE;
    private Integer weekWinGamesPvE;
    private Integer monthGamesPvE;
    private Integer monthWinGamesPvE;
}
