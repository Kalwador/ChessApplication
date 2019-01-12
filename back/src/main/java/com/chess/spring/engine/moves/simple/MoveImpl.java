package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MoveImpl extends AbstractMove {

    public MoveImpl(Board board, AbstractPiece pieceMoved, int destinationCoordinate) {
        super(board, pieceMoved, destinationCoordinate);
    }

    @Override
    public String toString() {
        return getPiece().getType().toString() + getSimpleFile() + BoardService.INSTANCE.getPositionAtCoordinate(getDestination());
    }

}