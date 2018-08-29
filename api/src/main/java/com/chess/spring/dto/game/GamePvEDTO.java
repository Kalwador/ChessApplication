package com.chess.spring.dto.game;

import com.chess.spring.entities.GamePvE;
import com.chess.spring.models.game.PlayerColor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GamePvEDTO {
    @NotNull
    private PlayerColor color;

    @NotNull
    @Size(min = 1, max = 10)
    private Integer level;

    public static GamePvEDTO convert(GamePvE game) {
        GamePvEDTO gamePvEDTO = new GamePvEDTO();
        //TODO
        return gamePvEDTO;
    }
}
