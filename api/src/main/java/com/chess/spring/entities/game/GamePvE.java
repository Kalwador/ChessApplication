package com.chess.spring.entities.game;

import com.chess.spring.entities.account.Account;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GameWinner;
import com.chess.spring.models.status.GamePvEStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "game_pve")
public class GamePvE {
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
}
