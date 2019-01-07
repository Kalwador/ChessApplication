package com.chess.spring.dto.game;

import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.models.game.GamePvEStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePvEDTO {

    private Long id;

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
        return GamePvEDTO.builder()
                .id(game.getId())
                .level(game.getLevel())
                .color(game.getColor())
                .moves(game.getMoves())
                .board(game.getBoard())
                .status(game.getStatus())
//                .(game.)//TODO-TIMING
                .build();
    }

    public static Page<GamePvE> map(List<GamePvE> list) {
        return new PageImpl<>(list);
    }

    public static Page<GamePvEDTO> map(Page<GamePvE> all, Pageable page) {
        List<GamePvEDTO> games = all.getContent().stream().map(GamePvEDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }
}
