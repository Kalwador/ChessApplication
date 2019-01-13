package com.chess.spring.game.player;

import com.chess.spring.game.moves.simple.castle.KingSideCastleMove;
import com.chess.spring.game.moves.simple.castle.QueenSideCastleMove;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public  class BlackPlayer extends AbstractPlayer {

    public BlackPlayer( Board board,
                        List<AbstractMove> whiteStandardLegals,
                        List<AbstractMove> blackStandardLegals) {
        super(board, blackStandardLegals, whiteStandardLegals);
    }

    @Override
    protected List<AbstractMove> calculateKingCastles(List<AbstractMove> playerLegals,
                                                      List<AbstractMove> opponentLegals) {

        if (this.isInCheck() || this.isCastled() || !(this.isKingSideCastleCapable() || this.isQueenSideCastleCapable())) {
            return ImmutableList.of();
        }

         List<AbstractMove> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && this.playerKing.getPosition() == 4 && !this.isInCheck) {
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                 AbstractPiece kingSideRook = this.board.getPiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        AbstractPlayer.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        AbstractPlayer.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        kingSideRook.getType().isRook()) {
                    if (!BoardService.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPosition(), 5));

                    }
                }
            }
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                 AbstractPiece queenSideRook = this.board.getPiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        AbstractPlayer.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        AbstractPlayer.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        queenSideRook.getType().isRook()) {
                    if (!BoardService.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPosition(), 3));
                    }
                }
            }
        }
        return kingCastles;
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public List<AbstractPiece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public PlayerColor getAlliance() {
        return PlayerColor.BLACK;
    }

    @Override
    public String toString() {
        return PlayerColor.BLACK.toString();
    }

}
