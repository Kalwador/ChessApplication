package com.chess.spring.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    private Integer gamesPvP;
    private Integer winGamesPvP;
    private Integer weekGamesPvP;
    private Integer weekWinGamesPvP;
    private Integer monthGamesPvP;
    private Integer monthWinGamesPvP;

    private Integer gamesPvE;
    private Integer winGamesPvE;
    private Integer weekGamesPvE;
    private Integer weekWinGamesPvE;
    private Integer monthGamesPvE;
    private Integer monthWinGamesPvE;
}
