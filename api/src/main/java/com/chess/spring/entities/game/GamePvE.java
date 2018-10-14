package com.chess.spring.entities.game;

import com.chess.spring.entities.account.Account;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GameWinner;
import com.chess.spring.models.status.GamePvEStatus;
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
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @ApiModelProperty(notes = "player in current game")
    private Account account;

    private Long timePerMove;

    @Enumerated(EnumType.STRING)
    private PlayerColor color;

    private Integer level;

    private LocalDate gameStarted;

    private GamePvEStatus status;

    private String board;

    private String moves;

    private GameWinner gameWinner;

    private String permalink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePvE gamePvE = (GamePvE) o;
        return Objects.equals(id, gamePvE.id) &&
                Objects.equals(account, gamePvE.account) &&
                Objects.equals(timePerMove, gamePvE.timePerMove) &&
                color == gamePvE.color &&
                Objects.equals(level, gamePvE.level) &&
                Objects.equals(gameStarted, gamePvE.gameStarted) &&
                status == gamePvE.status &&
                Objects.equals(board, gamePvE.board) &&
                Objects.equals(moves, gamePvE.moves) &&
                gameWinner == gamePvE.gameWinner &&
                Objects.equals(permalink, gamePvE.permalink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, timePerMove, color, level, gameStarted, status, board, moves, gameWinner);
    }
}

