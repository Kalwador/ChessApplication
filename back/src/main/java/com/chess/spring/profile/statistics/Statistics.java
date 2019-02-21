package com.chess.spring.profile.statistics;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rank;

    @Column(name = "games_pvp")
    private Integer gamesPvP;
    @Column(name = "win_games_pvp")
    private Integer winGamesPvP;
    @Column(name = "week_games_pvp")
    private Integer weekGamesPvP;
    @Column(name = "week_win_games_pvp")
    private Integer weekWinGamesPvP;
    @Column(name = "month_games_pvp")
    private Integer monthGamesPvP;
    @Column(name = "month_win_games_pvp")
    private Integer monthWinGamesPvP;

    @Column(name = "games_pve")
    private Integer gamesPvE;
    @Column(name = "win_games_pve")
    private Integer winGamesPvE;
    @Column(name = "week_games_pve")
    private Integer weekGamesPvE;
    @Column(name = "week_win_games_pve")
    private Integer weekWinGamesPvE;
    @Column(name = "month_games_pve")
    private Integer monthGamesPvE;
    @Column(name = "month_win_games_pve")
    private Integer monthWinGamesPvE;
}
