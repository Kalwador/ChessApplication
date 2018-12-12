package com.chess.spring.engine.board;

import com.chess.spring.engine.pieces.PieceColor;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.move.Move.MoveFactory;
import com.chess.spring.engine.pieces.*;
import com.chess.spring.engine.player.BlackPlayerImpl;
import com.chess.spring.engine.player.Player;
import com.chess.spring.engine.player.WhitePlayerImpl;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public  class Board {

    private  Map<Integer, Piece> boardConfig;
    private  Collection<Piece> whitePieces;
    private  Collection<Piece> blackPieces;
    private WhitePlayerImpl whitePlayerImpl;
    private BlackPlayerImpl blackPlayerImpl;
    private  Player currentPlayer;
    private  Pawn enPassantPawn;
    private  Move transitionMove;

    private static  Board STANDARD_BOARD = createStandardBoardImpl();

    private Board( Builder builder) {
        this.boardConfig = builder.boardConfig;
        this.whitePieces = calculateActivePieces(builder, PieceColor.WHITE);
        this.blackPieces = calculateActivePieces(builder, PieceColor.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
         Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
         Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayerImpl = new WhitePlayerImpl(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayerImpl = new BlackPlayerImpl(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayerImpl, this.blackPlayerImpl);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : MoveFactory.getNullMove();
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

    private static String prettyPrint( Piece piece) {
        if(piece != null) {
            return piece.getPieceAllegiance().isBlack() ?
                    piece.toString().toLowerCase() : piece.toString();
        }
        return "-";
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Iterable<Piece> getAllPieces() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePieces, this.blackPieces));
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayerImpl.getLegalMoves(),
                                                               this.blackPlayerImpl.getLegalMoves()));
    }

    public WhitePlayerImpl whitePlayer() {
        return this.whitePlayerImpl;
    }

    public BlackPlayerImpl blackPlayer() {
        return this.blackPlayerImpl;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Piece getPiece( int coordinate) {
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
         Builder builder = new Builder();
        // Black Layout
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
        // White Layout
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
        //white to move
        builder.setMoveMaker(PieceColor.WHITE);
        //build the board
        return builder.build();
    }

    private Collection<Move> calculateLegalMoves( Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                      .collect(Collectors.toList());
    }

    private static Collection<Piece> calculateActivePieces( Builder builder,
                                                            PieceColor pieceColor) {
        return builder.boardConfig.values().stream()
               .filter(piece -> piece.getPieceAllegiance() == pieceColor)
               .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        PieceColor nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>(33, 1.0f);
        }

        public Builder setPiece( Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker( PieceColor nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setEnPassantPawn( Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public Builder setMoveTransition( Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }

}
