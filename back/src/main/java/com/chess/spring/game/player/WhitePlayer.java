package com.chess.spring.game.player;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.simple.castle.KingSideCastleMove;
import com.chess.spring.game.moves.simple.castle.QueenSideCastleMove;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.pieces.Rook;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class WhitePlayer extends AbstractPlayer {

    public WhitePlayer(Board board,
                       List<AbstractMove> whiteStandardLegals,
                       List<AbstractMove> blackStandardLegals) {
        super(board, whiteStandardLegals, blackStandardLegals);
    }

    @Override
    protected List<AbstractMove> calculateKingCastles(List<AbstractMove> playerLegals,
                                                            List<AbstractMove> opponentLegals) {

        if (this.isInCheck() || this.isCastled() || !(this.isKingSideCastleCapable() || this.isQueenSideCastleCapable())) {
            return ImmutableList.of();
        }

        List<AbstractMove> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPosition() == 60 && !this.isInCheck()) {
            if (this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                AbstractPiece kingSideRook = this.board.getPiece(63);
                if (kingSideRook != null && kingSideRook.isFirstMove()) {
                    if (AbstractPlayer.calculateAttacksOnTile(61, opponentLegals).isEmpty() && AbstractPlayer.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                            kingSideRook.getType().isRook()) {
                        if (!BoardService.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPosition(), 61));
                        }
                    }
                }
            }
            if (this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
                    this.board.getPiece(57) == null) {
                AbstractPiece queenSideRook = this.board.getPiece(56);
                if (queenSideRook != null && queenSideRook.isFirstMove()) {
                    if (AbstractPlayer.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                            AbstractPlayer.calculateAttacksOnTile(59, opponentLegals).isEmpty() && queenSideRook.getType().isRook()) {
                        if (!BoardService.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPosition(), 59));
                        }
                    }
                }
            }
        }
        return kingCastles;
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public List<AbstractPiece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public PlayerColor getAlliance() {
        return PlayerColor.WHITE;
    }

    @Override
    public String toString() {
        return PlayerColor.WHITE.toString();
    }

}
