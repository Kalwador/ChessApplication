package com.chess.spring.engine.ai.analyzers;

import com.chess.spring.engine.pieces.Piece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceDistance {
    private Piece enemyPiece;
    private int distance;
}