package com.chess.spring.game.board;

import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.moves.ErrorMove;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Pawn;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.player.AbstractPlayer;
import com.chess.spring.game.player.BlackPlayer;
import com.chess.spring.game.player.WhitePlayer;
import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = false)
public class Board {

    private Map<Integer, AbstractPiece> config;
    private List<AbstractPiece> whitePieces;
    private List<AbstractPiece> blackPieces;
    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;
    private AbstractPlayer currentPlayer;
    private Pawn passingAttack;
    private AbstractMove transition;

    public Board(final BoardBuilder builder) {
        this.config = builder.getConfiguration();
        this.whitePieces = BoardUtils.calculateActivePieces(builder, PlayerColor.WHITE);
        this.blackPieces = BoardUtils.calculateActivePieces(builder, PlayerColor.BLACK);
        this.passingAttack = builder.getPawn();
        List<AbstractMove> whiteStandardMoves = BoardUtils.calculateLegalMoves(this, this.whitePieces);
        List<AbstractMove> blackStandardMoves = BoardUtils.calculateLegalMoves(this, this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.getNextPlayer().choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transition = Optional.ofNullable(builder.getMove()).orElse(ErrorMove.getInstance());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardConfiguration.TILES_MAX; i++) {
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

    public List<AbstractPiece> getBlackPieces() {
        return this.blackPieces;
    }

    public List<AbstractPiece> getWhitePieces() {
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
