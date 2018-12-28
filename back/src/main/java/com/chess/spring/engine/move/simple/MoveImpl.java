package com.chess.spring.engine.move.simple;

import com.chess.spring.engine.board.PieceConfiguration;
import com.chess.spring.engine.pieces.PieceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class MoveImpl {
    private int source;
    private int destination;
    private PieceType type;

    @Override
    public String toString() {
        return PieceConfiguration.getPositionAtCoordinate(this.source) + "-" + PieceConfiguration.getPositionAtCoordinate(this.destination);
    }
}
