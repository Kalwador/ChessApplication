package com.chess.spring.entities.game;

import com.chess.spring.entities.account.Account;
import com.chess.spring.models.game.GamePvPStatus;
import com.chess.spring.models.game.PlayerColor;
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
@Table(name = "game_pvp")
public class GamePvP extends Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "black_id")
    @ApiModelProperty(notes = "player in current game")
    private Account blackPlayer;

    @ManyToOne
    @JoinColumn(name = "white_id")
    @ApiModelProperty(notes = "player in current game")
    private Account whitePlayer;

    private Long timePerMove;

    @Enumerated(EnumType.STRING)
    private PlayerColor color;

    private Integer level;

    private LocalDate gameStarted;

    private GamePvPStatus status;

    private String board;

    private String moves;

    private String permalink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePvP gamePvP = (GamePvP) o;
        return Objects.equals(id, gamePvP.id) &&
                Objects.equals(whitePlayer, gamePvP.whitePlayer) &&
                Objects.equals(blackPlayer, gamePvP.blackPlayer) &&
                Objects.equals(timePerMove, gamePvP.timePerMove) &&
                color == gamePvP.color &&
                Objects.equals(level, gamePvP.level) &&
                Objects.equals(gameStarted, gamePvP.gameStarted) &&
                status == gamePvP.status &&
                Objects.equals(board, gamePvP.board) &&
                Objects.equals(moves, gamePvP.moves) &&
                Objects.equals(permalink, gamePvP.permalink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, whitePlayer, blackPlayer, timePerMove, color, level, gameStarted, status, board, moves);
    }
}

