package com.chess.spring.profile.statistics.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class SchedulerTask {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "0 0 0 * * MON")
    public void updateWeekStatistic() {
        log.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(ZonedDateTime.now()));
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateMonthStatistic() {
        log.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(ZonedDateTime.now()));
    }

//    @Scheduled(cron = "0 0 0 1 ?/1 ?")
//    public void updateYearStatistic() {
//        log.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(ZonedDateTime.now()));
//    }
}
