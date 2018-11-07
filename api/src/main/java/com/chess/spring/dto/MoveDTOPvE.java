package com.chess.spring.dto;

import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.models.game.GamePvEStatus;
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
public class MoveDTOPvE {
    private Integer source;
    private Integer destination;
    private String type;
    private GamePvEStatus status;
    private String moveLog;

    public static MoveDTOPvE map(Move move, boolean isInCheck) {
        MoveDTOPvE moveDTOPvE = new MoveDTOPvE();
        moveDTOPvE.setSource(move.getCurrentCoordinate());
        moveDTOPvE.setDestination(move.getDestinationCoordinate());
        moveDTOPvE.setType(move.getClass().getSimpleName());
        moveDTOPvE.setStatus(isInCheck ? GamePvEStatus.CHECK : GamePvEStatus.PLAYER_MOVE);
        return moveDTOPvE;
    }

    private static MoveDTOPvE mapSimple(Move move) {
        MoveDTOPvE moveDTOPvE = new MoveDTOPvE();
        moveDTOPvE.setSource(move.getCurrentCoordinate());
        moveDTOPvE.setDestination(move.getDestinationCoordinate());
        return moveDTOPvE;
    }

    public static List<MoveDTOPvE> map(Iterable<Move> moves) {
        return StreamSupport.stream(moves.spliterator(), false).map(MoveDTOPvE::mapSimple).collect(Collectors.toList());
    }
}
