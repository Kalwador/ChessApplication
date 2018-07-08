package com.chess.spring.entities;

import com.chess.spring.models.player.PlayerColor;
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
@Table(name = "GAME")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long id;

    private LocalDate timeMoveStarted;

    @ManyToOne
    @JoinColumn(name = "BLACK_PLAYER_ID")
    @ApiModelProperty(notes = "black player in current game")
    private Player blackPlayer;

    @ManyToOne
    @JoinColumn(name = "WHITE_PLAYER_ID")
    @ApiModelProperty(notes = "white player in current game")
    private Player whitePlayer;
}
