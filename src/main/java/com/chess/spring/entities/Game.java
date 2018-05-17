package com.chess.spring.entities;

import com.chess.spring.models.PlayerColor;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long id;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Map<PlayerColor, Player> players;


    private Integer status;
    private LocalDate timeMoveStarted;
}
