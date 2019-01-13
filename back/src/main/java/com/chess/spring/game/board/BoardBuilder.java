package com.chess.spring.game.board;

import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Pawn;
import com.chess.spring.game.pieces.utils.PlayerColor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class BoardBuilder {

    private Map<Integer, AbstractPiece> configuration;
    private PlayerColor nextPlayer;
    private Pawn pawn;
    private AbstractMove move;

    public BoardBuilder() {
        this.configuration = new HashMap<>(33, 1.0f);
    }

    public BoardBuilder setPiece( AbstractPiece piece) {
        this.configuration.put(piece.getPosition(), piece);
        return this;
    }

    public BoardBuilder setMoveMaker( PlayerColor nextMoveMaker) {
        this.nextPlayer = nextMoveMaker;
        return this;
    }

    public BoardBuilder setPawn( Pawn pawn) {
        this.pawn = pawn;
        return this;
    }

    public BoardBuilder setMoveTransition( AbstractMove transitionMove) {
        this.move = transitionMove;
        return this;
    }

    public Board build() {
        return new Board(this);
    }

}