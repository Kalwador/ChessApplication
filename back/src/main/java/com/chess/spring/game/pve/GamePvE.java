package com.chess.spring.game.pve;

import com.chess.spring.game.Game;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.profile.account.Account;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "game_pve")
public class GamePvE extends Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @ApiModelProperty(notes = "player in current game")
    private Account account;

    @Column(name = "time_per_move")
    private Long timePerMove;

    @Enumerated(EnumType.STRING)
    private PlayerColor color;

    private Integer level;

    @Column(name = "game_started")
    private LocalDate gameStarted;

    @Enumerated(value = EnumType.STRING)
    private GamePvEStatus status;

    private String board;

    private String moves;

    @Column(unique = true)
    private String permalink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePvE gamePvE = (GamePvE) o;
        return Objects.equals(id, gamePvE.id) &&
                Objects.equals(timePerMove, gamePvE.timePerMove) &&
                color == gamePvE.color &&
                Objects.equals(level, gamePvE.level) &&
                Objects.equals(gameStarted, gamePvE.gameStarted) &&
                status == gamePvE.status &&
                Objects.equals(board, gamePvE.board) &&
                Objects.equals(moves, gamePvE.moves) &&
                Objects.equals(permalink, gamePvE.permalink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timePerMove, color, level, gameStarted, status, board, moves);
    }
}

