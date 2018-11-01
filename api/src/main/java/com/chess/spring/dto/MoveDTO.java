package com.chess.spring.dto;

import com.chess.spring.engine.classic.board.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class MoveDTO {
    private Integer source;
    private Integer destination;
    private String type;

    public static MoveDTO map(Move move) {
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setSource(move.getCurrentCoordinate());
        moveDTO.setDestination(move.getDestinationCoordinate());
        moveDTO.setType(move.getClass().getSimpleName());
        return moveDTO;
    }

    public static List<MoveDTO> map(Iterable<Move> moves) {
        return StreamSupport.stream(moves.spliterator(), false).map(MoveDTO::map).collect(Collectors.toList());
    }
}
