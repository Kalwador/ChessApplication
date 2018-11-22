package com.chess.spring.dto;

import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.models.game.GamePvEStatus;
import com.chess.spring.models.game.GamePvPStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class MoveDTO {
    private Integer source;
    private Integer destination;
    private String type;
    private boolean isInCheck;

    @JsonAlias(value = "status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GamePvEStatus statusPve;

    @JsonAlias(value = "status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GamePvPStatus statusPvP;

    private String moveLog;

    public static MoveDTO map(Move move, boolean isInCheck) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        moveDTO.setType(move.getClass().getSimpleName());
        return moveDTO;
    }

    private static MoveDTO mapSimple(Move move) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        return moveDTO;
    }

    public static List<MoveDTO> map(Iterable<Move> moves) {
        return StreamSupport.stream(moves.spliterator(), false).map(MoveDTO::mapSimple).collect(Collectors.toList());
    }
}
