package com.chess.spring.dto.game;

import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import com.chess.spring.models.game.PlayerColor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GamePvPDTO {
    private Long gameId;

    @NotNull
    private PlayerColor color;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer level;

    private String board;
    private String moves;
    private GamePvPStatus status;

    public static GamePvPDTO convert(GamePvP game) {
        GamePvPDTO gamePvPDTO = new GamePvPDTO();
        gamePvPDTO.setGameId(game.getId());
        gamePvPDTO.setLevel(game.getLevel());
        gamePvPDTO.setColor(game.getColor());
        gamePvPDTO.setMoves(game.getMoves());
        gamePvPDTO.setBoard(game.getBoard());
        gamePvPDTO.setStatus(game.getStatus());
//        gamePvEDTO.set(game.getTimePerMove());//TODO-TIMING
        return gamePvPDTO;
    }
}
