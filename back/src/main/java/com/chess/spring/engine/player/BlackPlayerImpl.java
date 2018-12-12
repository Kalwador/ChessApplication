package com.chess.spring.engine.player;

import com.chess.spring.engine.pieces.PieceColor;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.move.Move.KingSideCastleMove;
import com.chess.spring.engine.move.Move.QueenSideCastleMove;
import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public  class BlackPlayerImpl extends Player {

    public BlackPlayerImpl(Board board,
                           Collection<Move> whiteStandardLegals,
                           Collection<Move> blackStandardLegals) {
        super(board, blackStandardLegals, whiteStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles( Collection<Move> playerLegals,
                                                     Collection<Move> opponentLegals) {

        if (this.isInCheck() || this.isCastled() || !(this.isKingSideCastleCapable() || this.isQueenSideCastleCapable())) {
            return Collections.emptyList();
        }

         List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck) {
            //blacks king side castle
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                 Piece kingSideRook = this.board.getPiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        kingSideRook.getPieceType().isRook()) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 5));

                    }
                }
            }
            //blacks queen side castle
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                 Piece queenSideRook = this.board.getPiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        queenSideRook.getPieceType().isRook()) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public WhitePlayerImpl getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public PieceColor getAlliance() {
        return PieceColor.BLACK;
    }

    @Override
    public String toString() {
        return PieceColor.BLACK.toString();
    }

}
