package com.chess.spring.engine.pieces;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KingDistance {
    private AbstractPiece enemyPiece;
    private int distance;
}