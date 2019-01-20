package com.chess.spring.profile.statistics;

import com.chess.spring.profile.account.Account;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.game.GameEndType;
import com.chess.spring.communication.event.UpdateStatisticsEvent;
import com.chess.spring.game.GameType;
import com.chess.spring.profile.account.AccountService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements ApplicationListener<UpdateStatisticsEvent> {

    private AccountService accountService;
    private StatisticsRepository statisticsRepository;

    public StatisticsService(AccountService accountService,
                             StatisticsRepository statisticsRepository) {
        this.accountService = accountService;
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public void onApplicationEvent(UpdateStatisticsEvent updateStatisticsEvent) {
        try {
            Statistics statistics = accountService.getById(updateStatisticsEvent.getPlayerId()).getStatistics();
            if (updateStatisticsEvent.getGameType() == GameType.PVE) {
                statistics.setGamesPvE(statistics.getGamesPvE() + 1);
                statistics.setMonthGamesPvE(statistics.getMonthGamesPvE() + 1);
                statistics.setWeekGamesPvE(statistics.getWeekGamesPvE() + 1);
                if (updateStatisticsEvent.getEndType() == GameEndType.WIN) {
                    statistics.setWinGamesPvE(statistics.getWinGamesPvE() + 1);
                    statistics.setMonthWinGamesPvE(statistics.getMonthWinGamesPvE() + 1);
                    statistics.setWeekWinGamesPvE(statistics.getWeekWinGamesPvE() + 1);
                }
            } else {
                statistics.setGamesPvP(statistics.getGamesPvP() + 1);
                statistics.setMonthGamesPvP(statistics.getMonthGamesPvP() + 1);
                statistics.setWeekGamesPvP(statistics.getWeekGamesPvP() + 1);
                if (updateStatisticsEvent.getEndType() == GameEndType.WIN) {
                    statistics.setWinGamesPvP(statistics.getWinGamesPvP() + 1);
                    statistics.setMonthWinGamesPvP(statistics.getMonthWinGamesPvP() + 1);
                    statistics.setWeekWinGamesPvP(statistics.getWeekWinGamesPvP() + 1);
                }
                Account account = accountService.getById(updateStatisticsEvent.getPlayerId());
                statistics.setRank(calculateNewRank(account,updateStatisticsEvent.getEndType(),updateStatisticsEvent.getEnemyRank()));
            }
            statisticsRepository.save(statistics);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

    }


    private Integer calculateNewRank(Account account, GameEndType gameEndType, Integer enemyRank) {
        Double newRank = account.getStatistics().getRank() + getRatingCoeficient(account) * (getScore(gameEndType) - getExpectedScore(account.getStatistics().getRank(), enemyRank));
        return newRank.intValue();
    }

    /**
     * Source: https://ratings.fide.com/calculator_rtd.phtml
     *
     * @param account
     * @return
     */
    private Integer getRatingCoeficient(Account account) {
        //TODO-STATISTICS jeśli gra jest typu BLITZ lub BULLET to wtedy rating coeficient zawsze 20
        if (account.getStatistics().getGamesPvP() < 30) {
            return 40;
        }
        if (account.getAge() < 18 && account.getStatistics().getRank() < 2300) {
            return 40;
        }
        if (account.getStatistics().getRank() < 2400) {
            return 20;
        }
        return 10;
    }

    private double getScore(GameEndType gameEndType) {
        switch (gameEndType) {
            case WIN:
                return 1;
            case LOSE:
                return 0;
            case DRAW:
                return 0.5;
        }
        return 0;
    }

    /**
     * https://www.fide.com/fide/handbook.html?id=172&view=article
     * https://www.geeksforgeeks.org/elo-rating-algorithm/
     */
    private Double getExpectedScore(Integer rankA, Integer rankB) {
        return 1 / (10 * ((-1 * (rankA - rankB)) / 400) + 1.0);
    }
}
