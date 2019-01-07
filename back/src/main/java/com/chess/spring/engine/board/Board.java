package com.chess.spring.engine.board;

import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.moves.MoveService;
import com.chess.spring.engine.pieces.*;
import com.chess.spring.engine.player.BlackPlayer;
import com.chess.spring.engine.player.WhitePlayer;
import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = false)
public final class Board {

    private Map<Integer, AbstractPiece> config;
    private Collection<AbstractPiece> whitePieces;
    private Collection<AbstractPiece> blackPieces;
    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;
    private AbstractPlayer currentPlayer;
    private Pawn enPassant;
    private AbstractMove transition;

    public Board(final BoardBuilder builder) {
        this.config = builder.getConfiguration();
        this.whitePieces = BoardUtils.calculateActivePieces(builder, PlayerColor.WHITE);
        this.blackPieces = BoardUtils.calculateActivePieces(builder, PlayerColor.BLACK);
        this.enPassant = builder.getPawn();
        Collection<AbstractMove> whiteStandardMoves = BoardUtils.calculateLegalMoves(this, this.whitePieces);
        Collection<AbstractMove> blackStandardMoves = BoardUtils.calculateLegalMoves(this, this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.getNextPlayer().choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transition = Optional.ofNullable(builder.getMove()).orElse(MoveService.getNullMove());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardService.NUM_TILES; i++) {
            final String tileText = prettyPrint(this.config.get(i));
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static String prettyPrint(final AbstractPiece piece) {
        if (piece != null) {
            return piece.getPieceAllegiance().isBlack() ?
                    piece.toString().toLowerCase() : piece.toString();
        }
        return "-";
    }

    public Collection<AbstractPiece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<AbstractPiece> getWhitePieces() {
        return this.whitePieces;
    }

    public Iterable<AbstractPiece> getAllPieces() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePieces, this.blackPieces));
    }

    public Iterable<AbstractMove> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                this.blackPlayer.getLegalMoves()));
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }

    public AbstractPiece getPiece(final int coordinate) {
        return this.config.get(coordinate);
    }

    public static Board createStandardBoard() {
        return BoardUtils.createStandardBoardImpl();
    }

}
