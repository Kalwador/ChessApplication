package com.chess.spring.engine.board;

import com.chess.spring.engine.classic.PieceColor;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.move.Move.MoveFactory;
import com.chess.spring.models.pieces.*;
import com.chess.spring.engine.classic.player.BlackPlayer;
import com.chess.spring.engine.classic.player.Player;
import com.chess.spring.engine.classic.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Board {

    private Map<Integer, Piece> boardConfig;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;
    private Player currentPlayer;
    private Pawn enPassantPawn;
    private Move transitionMove;

    private static Board STANDARD_BOARD = createStandardBoardImpl();

    public Board(BoardBuilder boardBuilder) {
        this.boardConfig = boardBuilder.boardConfig;
        this.whitePieces = calculateActivePieces(boardBuilder, PieceColor.WHITE);
        this.blackPieces = calculateActivePieces(boardBuilder, PieceColor.BLACK);
        this.enPassantPawn = boardBuilder.enPassantPawn;
        Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = boardBuilder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transitionMove = boardBuilder.transitionMove != null ? boardBuilder.transitionMove : MoveFactory.getNullMove();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            String tileText = prettyPrint(this.boardConfig.get(i));
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static String prettyPrint(Piece piece) {
        if (piece != null) {
            return piece.getPieceAllegiance().isBlack() ?
                    piece.toString().toLowerCase() : piece.toString();
        }
        return "-";
    }

    public Iterable<Piece> getAllPieces() {
        return Iterables.concat(this.whitePieces, this.blackPieces);
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                this.blackPlayer.getLegalMoves()));
    }

    public Piece getPiece(int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    public static Board createStandardBoard() {
        return STANDARD_BOARD;
    }

    private static Board createStandardBoardImpl() {
        BoardBuilder boardBuilder = new BoardBuilder();
        // Black Layout
        boardBuilder.setPiece(new Rook(PieceColor.BLACK, 0));
        boardBuilder.setPiece(new Knight(PieceColor.BLACK, 1));
        boardBuilder.setPiece(new Bishop(PieceColor.BLACK, 2));
        boardBuilder.setPiece(new Queen(PieceColor.BLACK, 3));
        boardBuilder.setPiece(new King(PieceColor.BLACK, 4, true, true));
        boardBuilder.setPiece(new Bishop(PieceColor.BLACK, 5));
        boardBuilder.setPiece(new Knight(PieceColor.BLACK, 6));
        boardBuilder.setPiece(new Rook(PieceColor.BLACK, 7));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 8));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 9));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 10));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 11));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 12));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 13));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 14));
        boardBuilder.setPiece(new Pawn(PieceColor.BLACK, 15));
        // White Layout
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 48));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 49));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 50));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 51));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 52));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 53));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 54));
        boardBuilder.setPiece(new Pawn(PieceColor.WHITE, 55));
        boardBuilder.setPiece(new Rook(PieceColor.WHITE, 56));
        boardBuilder.setPiece(new Knight(PieceColor.WHITE, 57));
        boardBuilder.setPiece(new Bishop(PieceColor.WHITE, 58));
        boardBuilder.setPiece(new Queen(PieceColor.WHITE, 59));
        boardBuilder.setPiece(new King(PieceColor.WHITE, 60, true, true));
        boardBuilder.setPiece(new Bishop(PieceColor.WHITE, 61));
        boardBuilder.setPiece(new Knight(PieceColor.WHITE, 62));
        boardBuilder.setPiece(new Rook(PieceColor.WHITE, 63));
        //white to move
        boardBuilder.setMoveMaker(PieceColor.WHITE);
        //build the board
        return boardBuilder.build();
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                .collect(Collectors.toList());
    }

    private static Collection<Piece> calculateActivePieces(BoardBuilder boardBuilder,
                                                           PieceColor pieceColor) {
        return boardBuilder.boardConfig.values().stream()
                .filter(piece -> piece.getPieceAllegiance() == pieceColor)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

}
