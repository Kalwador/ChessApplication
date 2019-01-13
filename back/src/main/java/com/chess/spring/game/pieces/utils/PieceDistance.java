package com.chess.spring.game.pieces.utils;

import com.chess.spring.game.pieces.AbstractPiece;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PieceDistance {
    private AbstractPiece enemyPiece;
    private int distance;
}