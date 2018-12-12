package com.chess.spring.dto;

import com.chess.spring.entities.account.Statistics;
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
                .gamesPvP(statistics.getGamesPvE())
                .winGamesPvP(statistics.getWinGamesPvE())
                .weekGamesPvP(statistics.getMonthGamesPvE())
                .weekWinGamesPvP(statistics.getMonthWinGamesPvE())
                .monthGamesPvP(statistics.getWeekGamesPvE())
                .monthWinGamesPvP(statistics.getWeekWinGamesPvE())
                .gamesPvE(statistics.getGamesPvP())
                .winGamesPvE(statistics.getWinGamesPvP())
                .weekGamesPvE(statistics.getMonthGamesPvP())
                .weekWinGamesPvE(statistics.getMonthWinGamesPvP())
                .monthGamesPvE(statistics.getWeekGamesPvP())
                .monthWinGamesPvE(statistics.getWeekWinGamesPvP())
                .build();
    }
}
