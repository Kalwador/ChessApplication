package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.PieceConfiguration;
import com.chess.spring.engine.pieces.utils.PieceGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Move {
    private int source;
    private int destination;
    private PieceGenre type;

    @Override
    public String toString() {
        return PieceConfiguration.getPositionAtCoordinate(this.source) + "-" + PieceConfiguration.getPositionAtCoordinate(this.destination);
    }
}
