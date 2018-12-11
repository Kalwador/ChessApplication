package com.chess.spring.dto.game;

import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.game.GamePvPStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePvPDTO {
    private Long id;
    private Long white;
    private Long black;
    private String board;
    private String moves;
    private GamePvPStatus status;


    public static GamePvPDTO map(GamePvP game) {
        return GamePvPDTO.builder()
                .id(game.getId())
                .white(Optional.ofNullable(game.getWhitePlayer()).map(Account::getId).orElse(null))
                .black(Optional.ofNullable(game.getBlackPlayer()).map(Account::getId).orElse(null))
                .board(game.getBoard())
                .moves(game.getMoves())
                .status(game.getStatus())
                .build();
    }

    public static Page<GamePvPDTO> map(Page<GamePvP> all, Pageable page) {
        List<GamePvPDTO> games = all.stream().map(GamePvPDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }

    public static Page<GamePvPDTO> map(List<GamePvP> all, Pageable page) {
        List<GamePvPDTO> games = all.stream().map(GamePvPDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }
}
