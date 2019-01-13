package com.chess.spring.game.moves.simple;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.pieces.AbstractPiece;
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