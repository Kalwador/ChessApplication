package com.chess.spring.communication.event;

import com.chess.spring.game.GameEndType;
import com.chess.spring.game.GameType;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Data
@ToString
public class UpdateStatisticsEvent extends ApplicationEvent {
    private Long playerId;
    private GameType gameType;
    private GameEndType endType;
    private Integer enemyRank;

    public UpdateStatisticsEvent(Object source) {
        super(source);
    }

    public UpdateStatisticsEvent(Object source, Long playerId, GameType gameType, GameEndType endType, Integer enemyRank) {
        super(source);
        this.playerId = playerId;
        this.gameType = gameType;
        this.endType = endType;
        this.enemyRank = enemyRank;
    }
}
