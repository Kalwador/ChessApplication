//package com.chess.spring.entities;
//
//import com.chess.spring.models.account.PlayerColor;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Builder
//@Table(name = "game")
//public class Game {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "game_id")
//    private Long id;
//
//    private LocalDate timeMoveStarted;
//
//    @ManyToOne
//    @JoinColumn(name = "black_player_id")
//    @ApiModelProperty(notes = "black account in current game")
//    private Account blackPlayer;
//
//    @ManyToOne
//    @JoinColumn(name = "white_player_id")
//    @ApiModelProperty(notes = "white account in current game")
//    private Account whitePlayer;
//}
