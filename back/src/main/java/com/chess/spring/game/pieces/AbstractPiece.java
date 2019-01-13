package com.chess.spring.game.pieces;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.simple.MoveImpl;
import com.chess.spring.game.moves.simple.attack.AttackMoveImpl;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.pieces.utils.PieceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode
@AllArgsConstructor
public abstract class AbstractPiece {

    private PieceType type;
    private int position;
    private PlayerColor color;
    private boolean isFirstMove;
    private int code;

    AbstractPiece(PieceType type,
                  PlayerColor color,
                  int position,
                  boolean isFirstMove) {
        this.type = type;
        this.position = position;
        this.color = color;
        this.isFirstMove = isFirstMove;
        this.code = generateCustomHash();
    }

    public PlayerColor getPieceAllegiance() {
        return this.color;
    }

    public int getPieceValue() {
        return this.type.getPieceValue();
    }

    public abstract int locationBonus();

    public abstract AbstractPiece movePiece(AbstractMove move);

    public abstract List<AbstractMove> getOptionalMoves(Board board);

    private int generateCustomHash() {
        int result = this.type.hashCode();
        result = 31 * result + this.color.hashCode();
        result = 31 * result + this.position;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
    }

    public void qualifyMove(Board board, List<AbstractMove> legalMoves, int candidateDestinationCoordinate) {
        if (BoardService.isValidTileCoordinate(candidateDestinationCoordinate)) {
            AbstractPiece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
            if (pieceAtDestination == null) {
                legalMoves.add(new MoveImpl(board, this, candidateDestinationCoordinate));
            } else {
                PlayerColor pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                if (getColor() != pieceAtDestinationAllegiance) {
                    legalMoves.add(new AttackMoveImpl(board, this, candidateDestinationCoordinate,
                            pieceAtDestination));
                }
            }
        }
    }
}