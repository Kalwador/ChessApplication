package com.chess.spring.engine.pieces.utils;

import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PieceDistance {
    private AbstractPiece enemyPiece;
    private int distance;
}