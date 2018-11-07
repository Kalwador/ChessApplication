package com.chess.spring.dto;

import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.models.game.GamePvEStatus;
import com.chess.spring.models.game.GamePvPStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveDTOPvP {
    private Integer source;
    private Integer destination;
    private String type;
    private boolean isInCheck;
    private GamePvPStatus status;
    private String moveLog;

    public static MoveDTOPvP map(Move move, boolean isInCheck) {
        MoveDTOPvP moveDTO = new MoveDTOPvP();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        moveDTO.setType(move.getClass().getSimpleName());
        return moveDTO;
    }

    private static MoveDTOPvP mapSimple(Move move) {
        MoveDTOPvP moveDTO = new MoveDTOPvP();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        return moveDTO;
    }

    public static List<MoveDTOPvP> map(Iterable<Move> moves) {
        return StreamSupport.stream(moves.spliterator(), false).map(MoveDTOPvP::mapSimple).collect(Collectors.toList());
    }
}
