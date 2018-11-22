package com.chess.spring.dto.game;

import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import com.chess.spring.models.game.PlayerColor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

    public static GamePvPDTO map(GamePvP game) {
        GamePvPDTO gamePvPDTO = new GamePvPDTO();
        gamePvPDTO.setGameId(game.getId());
        gamePvPDTO.setColor(game.getColor());
        gamePvPDTO.setMoves(game.getMoves());
        gamePvPDTO.setBoard(game.getBoard());
        gamePvPDTO.setStatus(game.getStatus());
//        gamePvEDTO.set(game.getTimePerMove());//TODO-TIMING
        return gamePvPDTO;
    }

    public static Page<GamePvPDTO> map(Page<GamePvP> all, Pageable page) {
        List<GamePvPDTO> games = all.stream().map(GamePvPDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }
}
