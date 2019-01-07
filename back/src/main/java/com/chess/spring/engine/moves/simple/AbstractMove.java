package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.exceptions.NotExpectedError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    public Board undo() {
        BoardBuilder builder = new BoardBuilder();
        for (AbstractPiece piece : this.board.getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setMoveMaker(this.board.getCurrentPlayer().getAlliance());
        return builder.build();
    }

    public String disambiguationFile() {
        for (AbstractMove move : this.board.getCurrentPlayer().getLegalMoves()) {
            if (move.getDestination() == this.destination && !this.equals(move) &&
                    this.piece.getType().equals(move.getPiece().getType())) {
                return BoardService.INSTANCE.getPositionAtCoordinate(this.piece.getPosition()).substring(0, 1);
            }
        }
        return "";
    }

}