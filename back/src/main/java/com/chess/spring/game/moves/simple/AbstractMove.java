package com.chess.spring.game.moves.simple;

import com.chess.spring.exceptions.NotExpectedError;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardBuilder;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.pieces.AbstractPiece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractMove {
    private Board board;
    private AbstractPiece piece;
    private int destination;
    private boolean isFirstMove;

    public AbstractMove(Board board, int destinationCoordinate) {
        this.board = board;
        this.destination = destinationCoordinate;
        this.piece = null;
        this.isFirstMove = false;
    }

    public AbstractMove(Board board,
                        AbstractPiece pieceMoved,
                        int destinationCoordinate) {
        this.board = board;
        this.destination = destinationCoordinate;
        this.piece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    public int getCurrentCoordinate() {
        return this.piece.getPosition();
    }

    public boolean isAttackMove() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public AbstractPiece getAttackedPiece() {
        return null;
    }

    public Board execute() throws NotExpectedError {
        BoardBuilder builder = new BoardBuilder();
        this.board.getCurrentPlayer().getActivePieces().stream().filter(piece -> !this.piece.equals(piece)).forEach(builder::setPiece);
        this.board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.piece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    public String getSimpleFile() {
        for (AbstractMove move : this.board.getCurrentPlayer().getLegalMoves()) {
            if (move.getDestination() == this.destination && !this.equals(move) &&
                    this.piece.getType().equals(move.getPiece().getType())) {
                return BoardService.getPositionAtCoordinate(this.piece.getPosition()).substring(0, 1);
            }
        }
        return "";
    }

}