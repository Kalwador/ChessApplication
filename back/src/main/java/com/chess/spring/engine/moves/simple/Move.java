package com.chess.spring.engine.moves.simple;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Move {
    private Board board;
    private AbstractPiece piece;
    private int destination;
    private boolean isFirstMove;

    public Move(Board board, int destinationCoordinate) {
        this.board = board;
        this.destination = destinationCoordinate;
        this.piece = null;
        this.isFirstMove = false;
    }

    public Move(Board board,
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

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public AbstractPiece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.piece.equals(piece)).forEach(builder::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.piece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    public Board undo() {
        BoardBuilder builder = new BoardBuilder();
        for (AbstractPiece piece : this.board.getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setMoveMaker(this.board.currentPlayer().getAlliance());
        return builder.build();
    }

    public String disambiguationFile() {
        for (Move move : this.board.currentPlayer().getLegalMoves()) {
            if (move.getDestination() == this.destination && !this.equals(move) &&
                    this.piece.getType().equals(move.getPiece().getType())) {
                return BoardUtils.INSTANCE.getPositionAtCoordinate(this.piece.getPosition()).substring(0, 1);
            }
        }
        return "";
    }

}