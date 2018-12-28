package com.chess.spring.engine.classic.player;

import com.chess.spring.engine.move.MoveStatus;
import com.chess.spring.engine.pieces.PieceColor;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.engine.move.MoveTransition;
import com.chess.spring.engine.pieces.King;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public abstract class AbstractPlayer {

    protected Board board;
    protected King playerKing;
    protected Collection<Move> legalMoves;
    protected boolean isInCheck;

    AbstractPlayer(Board board,
                   Collection<Move> playerLegals,
                   Collection<Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !AbstractPlayer.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegals).isEmpty();
        playerLegals.addAll(calculateKingCastles(playerLegals, opponentLegals));
        this.legalMoves = ImmutableList.copyOf(playerLegals);
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    private King establishKing() {
        return (King) getActivePieces().stream().filter(piece ->
                piece.getPieceType().isKing()).findAny().orElseThrow(RuntimeException::new);
    }

    private boolean hasEscapeMoves() {
        return this.legalMoves.stream().anyMatch(move -> makeMove(move).getStatus().isDone());
    }

    static Collection<Move> calculateAttacksOnTile(int tile,
                                                   Collection<Move> moves) {
        return moves.stream().filter(move -> move.getDestination() == tile)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    public MoveTransition makeMove(Move move) {
        if (!this.legalMoves.contains(move)) {
            return new MoveTransition(move, MoveStatus.ILLEGAL_MOVE, this.board, this.board);
        }
        Board transitionedBoard = move.execute();
        Collection<Move> kingAttacks = AbstractPlayer.calculateAttacksOnTile(
                transitionedBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionedBoard.currentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(move, MoveStatus.LEAVES_PLAYER_IN_CHECK, this.board, this.board);
        }
        return new MoveTransition(move, MoveStatus.DONE, this.board, transitionedBoard);
    }

    public MoveTransition unMakeMove(Move move) {
        return new MoveTransition(move, MoveStatus.DONE, this.board, move.undo());
    }

    public abstract Collection<AbstractPiece> getActivePieces();

    public abstract PieceColor getAlliance();

    public abstract AbstractPlayer getOpponent();

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}
