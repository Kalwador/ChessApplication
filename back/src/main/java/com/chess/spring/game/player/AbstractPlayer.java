package com.chess.spring.game.player;

import com.chess.spring.game.moves.MoveStatus;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.Transition;
import com.chess.spring.game.pieces.King;
import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.NotExpectedError;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public abstract class AbstractPlayer {

    protected Board board;
    protected King playerKing;
    protected List<AbstractMove> legalMoves;
    protected boolean isInCheck;

    AbstractPlayer(Board board,
                   List<AbstractMove> playerLegals,
                   List<AbstractMove> opponentLegals) {
        this.board = board;
        this.playerKing = findKing();
        this.isInCheck = !AbstractPlayer.calculateAttacksOnTile(this.playerKing.getPosition(), opponentLegals).isEmpty();
        playerLegals.addAll(calculateKingCastles(playerLegals, opponentLegals));
        this.legalMoves = playerLegals;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !canEscape();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !canEscape();
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

    public abstract List<AbstractPiece> getActivePieces();

    public abstract PlayerColor getAlliance();

    public abstract AbstractPlayer getOpponent();

    protected abstract List<AbstractMove> calculateKingCastles(List<AbstractMove> playerLegals, List<AbstractMove> opponentLegals);

    private King findKing() {
        return (King) getActivePieces().stream()
                .filter(piece -> piece.getType().isKing())
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private boolean canEscape() {
        return this.legalMoves.stream()
                .anyMatch(move -> makeMove(move).getStatus().isDone());
    }

    static List<AbstractMove> calculateAttacksOnTile(int tile, List<AbstractMove> moves) {
        return moves.stream()
                .filter(move -> move.getDestination() == tile)
                .collect(Collectors.toList());
    }

    public Transition makeMove(AbstractMove move) {
        if (!this.legalMoves.contains(move)) {
            return new Transition(move, MoveStatus.ILLEGAL, this.board, this.board);
        }
        try {
            Board transitionedBoard = move.execute();
            List<AbstractMove> kingAttacks = AbstractPlayer.calculateAttacksOnTile(
                    transitionedBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPosition(),
                    transitionedBoard.getCurrentPlayer().getLegalMoves());
            if (!kingAttacks.isEmpty()) {
                return new Transition(move, MoveStatus.CHECK, this.board, this.board);
            }
            return new Transition(move, MoveStatus.DONE, this.board, transitionedBoard);
        } catch (NotExpectedError notExpectedError) {
            throw new RuntimeException(ExceptionMessages.NOT_EXPECTED_ERROR.getInfo());
        }
    }
}
