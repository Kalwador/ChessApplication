package com.chess.spring.dto;

import com.chess.spring.models.status.GameStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStatusDTO {
    private GameStatus gameStatus;
    // TODO: 05/17/18 add info about board bit set
    private Long secondsTillMoveEnd;
}
