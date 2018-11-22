package com.chess.spring.dto.game;

import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.game.GamePvEStatus;
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

    public static GamePvEDTO map(GamePvE game) {
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

    public static Page<GamePvE> map(List<GamePvE> list){
        return new PageImpl<>(list);
    }

    public static Page<GamePvEDTO> map(Page<GamePvE> all, Pageable page) {
        List<GamePvEDTO> games = all.getContent().stream().map(GamePvEDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games,page, games.size());
    }
}
