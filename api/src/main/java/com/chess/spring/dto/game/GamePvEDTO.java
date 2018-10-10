package com.chess.spring.dto.game;

import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GamePvEStatus;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GamePvEDTO {

    private Long gameId;

    @NotNull
    private PlayerColor color;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer level;

    private String board;
    private String moves;
    private GamePvEStatus status;

    public static GamePvEDTO convert(GamePvE game) {
        GamePvEDTO gamePvEDTO = new GamePvEDTO();
        gamePvEDTO.setGameId(game.getId());
        gamePvEDTO.setLevel(game.getLevel());
        gamePvEDTO.setColor(game.getColor());
        gamePvEDTO.setMoves(game.getMoves());
        gamePvEDTO.setBoard(game.getBoard());
        gamePvEDTO.setStatus(game.getStatus());
//        gamePvEDTO.set(game.getTimePerMove());//TODO-TIMING
        return gamePvEDTO;
    }
}
