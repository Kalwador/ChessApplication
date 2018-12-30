package com.chess.spring.engine.board;

import com.chess.spring.engine.classic.player.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.Move;
import com.chess.spring.engine.moves.MoveFactory;
import com.chess.spring.engine.pieces.*;
import com.chess.spring.engine.classic.player.player.BlackPlayer;
import com.chess.spring.engine.classic.player.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
public final class Board {

    private final Map<Integer, AbstractPiece> boardConfig;
    private final Collection<AbstractPiece> whitePieces;
    private final Collection<AbstractPiece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final AbstractPlayer currentPlayer;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private static final Board STANDARD_BOARD = createStandardBoardImpl();

    public Board(final BoardBuilder builder) {
        this.boardConfig = builder.getConfiguration();
        this.whitePieces = calculateActivePieces(builder, PieceColor.WHITE);
        this.blackPieces = calculateActivePieces(builder, PieceColor.BLACK);
        this.enPassantPawn = builder.getPawn();
        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.getNextPlayer().choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transitionMove = Optional.ofNullable(builder.getMove()).orElse(MoveFactory.getNullMove());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = prettyPrint(this.boardConfig.get(i));
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static String prettyPrint(final AbstractPiece piece) {
        if(piece != null) {
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

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                                                               this.blackPlayer.getLegalMoves()));
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }

    public AbstractPlayer currentPlayer() {
        return this.currentPlayer;
    }

    public AbstractPiece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }

    public static Board createStandardBoard() {
        return STANDARD_BOARD;
    }

    private static Board createStandardBoardImpl() {
        final BoardBuilder builder = new BoardBuilder();
        builder.setPiece(new Rook(PieceColor.BLACK, 0));
        builder.setPiece(new Knight(PieceColor.BLACK, 1));
        builder.setPiece(new Bishop(PieceColor.BLACK, 2));
        builder.setPiece(new Queen(PieceColor.BLACK, 3));
        builder.setPiece(new King(PieceColor.BLACK, 4, true, true));
        builder.setPiece(new Bishop(PieceColor.BLACK, 5));
        builder.setPiece(new Knight(PieceColor.BLACK, 6));
        builder.setPiece(new Rook(PieceColor.BLACK, 7));
        builder.setPiece(new Pawn(PieceColor.BLACK, 8));
        builder.setPiece(new Pawn(PieceColor.BLACK, 9));
        builder.setPiece(new Pawn(PieceColor.BLACK, 10));
        builder.setPiece(new Pawn(PieceColor.BLACK, 11));
        builder.setPiece(new Pawn(PieceColor.BLACK, 12));
        builder.setPiece(new Pawn(PieceColor.BLACK, 13));
        builder.setPiece(new Pawn(PieceColor.BLACK, 14));
        builder.setPiece(new Pawn(PieceColor.BLACK, 15));
        builder.setPiece(new Pawn(PieceColor.WHITE, 48));
        builder.setPiece(new Pawn(PieceColor.WHITE, 49));
        builder.setPiece(new Pawn(PieceColor.WHITE, 50));
        builder.setPiece(new Pawn(PieceColor.WHITE, 51));
        builder.setPiece(new Pawn(PieceColor.WHITE, 52));
        builder.setPiece(new Pawn(PieceColor.WHITE, 53));
        builder.setPiece(new Pawn(PieceColor.WHITE, 54));
        builder.setPiece(new Pawn(PieceColor.WHITE, 55));
        builder.setPiece(new Rook(PieceColor.WHITE, 56));
        builder.setPiece(new Knight(PieceColor.WHITE, 57));
        builder.setPiece(new Bishop(PieceColor.WHITE, 58));
        builder.setPiece(new Queen(PieceColor.WHITE, 59));
        builder.setPiece(new King(PieceColor.WHITE, 60, true, true));
        builder.setPiece(new Bishop(PieceColor.WHITE, 61));
        builder.setPiece(new Knight(PieceColor.WHITE, 62));
        builder.setPiece(new Rook(PieceColor.WHITE, 63));
        builder.setMoveMaker(PieceColor.WHITE);
        return builder.build();
    }

    private Collection<Move> calculateLegalMoves(final Collection<AbstractPiece> pieces) {
        return pieces.stream().flatMap(piece -> piece.getOptionalMoves(this).stream())
                      .collect(Collectors.toList());
    }

    private static Collection<AbstractPiece> calculateActivePieces(final BoardBuilder builder,
                                                                   final PieceColor pieceColor) {
        return builder.getConfiguration().values().stream()
               .filter(piece -> piece.getPieceAllegiance() == pieceColor)
               .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

}
