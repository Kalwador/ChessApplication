package com.chess.spring.profile.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
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

    public static StatisticsDTO map(Statistics statistics) {
        return StatisticsDTO.builder()
                .rank(statistics.getRank())
                .gamesPvP(statistics.getGamesPvP())
                .winGamesPvP(statistics.getWinGamesPvP())
                .weekGamesPvP(statistics.getWeekGamesPvP())
                .weekWinGamesPvP(statistics.getWeekWinGamesPvP())
                .monthGamesPvP(statistics.getMonthGamesPvP())
                .monthWinGamesPvP(statistics.getMonthWinGamesPvP())
                .gamesPvE(statistics.getGamesPvE())
                .winGamesPvE(statistics.getWinGamesPvE())
                .weekGamesPvE(statistics.getWeekGamesPvE())
                .weekWinGamesPvE(statistics.getWeekWinGamesPvE())
                .monthGamesPvE(statistics.getMonthGamesPvE())
                .monthWinGamesPvE(statistics.getMonthWinGamesPvE())
                .build();
    }
}
