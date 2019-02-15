package com.chess.spring.game.pvp;

import com.chess.spring.communication.chat.Chat;
import com.chess.spring.game.Game;
import com.chess.spring.profile.account.Account;
import com.chess.spring.profile.invitations.Invitation;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "black_id")
    @ApiModelProperty(notes = "blackPlayer player in current game")
    private Account blackPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "white_id")
    @ApiModelProperty(notes = "whitePlayer player in current game")
    private Account whitePlayer;

    private Long timePerMove;

    private LocalDate gameStarted;

    @Enumerated(value = EnumType.STRING)
    private GamePvPStatus status;

    private String board;

    private String moves;

    private String permalink;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "game")
    private Chat chat;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "game")
    private Invitation invitation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePvP gamePvP = (GamePvP) o;
        return Objects.equals(id, gamePvP.id) &&
                Objects.equals(whitePlayer, gamePvP.whitePlayer) &&
                Objects.equals(blackPlayer, gamePvP.blackPlayer) &&
                Objects.equals(timePerMove, gamePvP.timePerMove) &&
                Objects.equals(gameStarted, gamePvP.gameStarted) &&
                status == gamePvP.status &&
                Objects.equals(board, gamePvP.board) &&
                Objects.equals(moves, gamePvP.moves) &&
                Objects.equals(permalink, gamePvP.permalink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, whitePlayer, blackPlayer, timePerMove, gameStarted, status, board, moves);
    }
}


