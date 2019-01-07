package com.chess.spring.dto;

import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.models.game.GamePvEStatus;
import com.chess.spring.models.game.GamePvPStatus;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveDTO {
    private Integer source;
    private Integer destination;

    //moveType - simple name of engine class
    private String type;
    private boolean isInCheck;

    @JsonIgnore
    private GamePvEStatus statusPve;
    @JsonIgnore
    private GamePvPStatus statusPvP;
    private String status;

    private String moveLog;

    public static MoveDTO map(AbstractMove move, boolean isInCheck) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestination());
        moveDTO.setType(move.getClass().getSimpleName());
        moveDTO.setInCheck(isInCheck);
        return moveDTO;
    }

    private static MoveDTO mapSimple(AbstractMove move) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestination());
        moveDTO.setStatus("");
        return moveDTO;
    }

    public static List<MoveDTO> map(Iterable<AbstractMove> moves) {
        return StreamSupport.stream(moves.spliterator(), false).map(MoveDTO::mapSimple).collect(Collectors.toList());
    }


    @JsonGetter
    public String getStatus() {
        if (status != null) {
            return status;
        } else {
            return this.statusPvP != null ? this.statusPvP.name() : this.statusPve.name();
        }
    }
}
