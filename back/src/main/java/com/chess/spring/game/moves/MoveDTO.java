package com.chess.spring.game.moves;

import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pve.GamePvEStatus;
import com.chess.spring.game.pvp.GamePvPStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //moveType - simple name of move class
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
