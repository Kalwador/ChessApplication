package com.chess.spring.engine.classic.player.player;

import com.chess.spring.engine.move.castle.KingSideCastleMove;
import com.chess.spring.engine.move.castle.QueenSideCastleMove;
import com.chess.spring.engine.pieces.PieceColor;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.chess.spring.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class WhitePlayer extends AbstractPlayer {

    public WhitePlayer( Board board,
                        Collection<Move> whiteStandardLegals,
                        Collection<Move> blackStandardLegals) {
        super(board, whiteStandardLegals, blackStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles( Collection<Move> playerLegals,
                                                     Collection<Move> opponentLegals) {

        if(this.isInCheck() || this.isCastled() || !(this.isKingSideCastleCapable() || this.isQueenSideCastleCapable())) {
            return ImmutableList.of();
        }

         List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck()) {
            //whites king side castle
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                 AbstractPiece kingSideRook = this.board.getPiece(63);
                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(AbstractPlayer.calculateAttacksOnTile(61, opponentLegals).isEmpty() && AbstractPlayer.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                       kingSideRook.getPieceType().isRook()) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
                        }
                    }
                }
            }
            //whites queen side castle
            if(this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
               this.board.getPiece(57) == null) {
                 AbstractPiece queenSideRook = this.board.getPiece(56);
                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(AbstractPlayer.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                       AbstractPlayer.calculateAttacksOnTile(59, opponentLegals).isEmpty() && queenSideRook.getPieceType().isRook()) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 59));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<AbstractPiece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public PieceColor getAlliance() {
        return PieceColor.WHITE;
    }

    @Override
    public String toString() {
        return PieceColor.WHITE.toString();
    }

}
