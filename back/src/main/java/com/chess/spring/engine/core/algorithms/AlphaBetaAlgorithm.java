package com.chess.spring.engine.core.algorithms;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.core.evaluators.EvaluatorService;
import com.chess.spring.engine.core.evaluators.EvaluatorServiceImpl;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.moves.MoveService;
import com.chess.spring.engine.moves.Transition;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Data
@Service
public class AlphaBetaAlgorithm extends Observable implements AbstractAlgorithm {

    private EvaluatorService evaluator;
    private long boardsEvaluated;
    private long executionTime;
    private int quiescenceCount;
    private static int MAX_QUIESCENCE = 5000 * 10;
    private EvaluatorServiceImpl defaultAbstractEvaluartor;

    public AlphaBetaAlgorithm(EvaluatorServiceImpl defaultAbstractEvaluartor) {
        this.evaluator = defaultAbstractEvaluartor;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public String toString() {
        return "AlphaBetaAlgorithm";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public AbstractMove execute(Board board, int level) {
        long startTime = System.currentTimeMillis();
        AbstractPlayer currentPlayer = board.getCurrentPlayer();
        AbstractMove bestMove = MoveService.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        int moveCounter = 1;
        int numMoves = board.getCurrentPlayer().getLegalMoves().size();

        for (AbstractMove move : MoveSorter.EXPENSIVE.sort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            String s;
            if (moveTransition.getStatus().isDone()) {
                long candidateMoveStartTime = System.nanoTime();
                currentValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getAfterMoveBoard(), level - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.getAfterMoveBoard(), level - 1, highestSeenValue, lowestSeenValue);
                if (currentPlayer.getAlliance().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getAfterMoveBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                } else if (currentPlayer.getAlliance().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getAfterMoveBoard().whitePlayer().isInCheckMate()) {
                        break;
                    }
                }
            }
            setChanged();
            moveCounter++;
        }

        this.executionTime = System.currentTimeMillis() - startTime;
        setChanged();
        return bestMove;
    }

    private static String score(AbstractPlayer currentPlayer,
                                int highestSeenValue,
                                int lowestSeenValue) {

        if (currentPlayer.getAlliance().isWhite()) {
            return "[score: " + highestSeenValue + "]";
        } else if (currentPlayer.getAlliance().isBlack()) {
            return "[score: " + lowestSeenValue + "]";
        }
        throw new RuntimeException("bad bad boy!");
    }

    private int max(Board board,
                    int depth,
                    int highest,
                    int lowest) {
        if (depth == 0 || BoardService.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentHighest = highest;
        for (AbstractMove move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                currentHighest = Math.max(currentHighest, min(moveTransition.getAfterMoveBoard(),
                        calculateQuiescenceDepth(moveTransition, depth), currentHighest, lowest));
                if (currentHighest >= lowest) {
                    return lowest;
                }
            }
        }
        return currentHighest;
    }

    private int min(Board board,
                    int depth,
                    int highest,
                    int lowest) {
        if (depth == 0 || BoardService.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentLowest = lowest;
        for (AbstractMove move : MoveSorter.STANDARD.sort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                currentLowest = Math.min(currentLowest, max(moveTransition.getAfterMoveBoard(),
                        calculateQuiescenceDepth(moveTransition, depth), highest, currentLowest));
                if (currentLowest <= highest) {
                    return highest;
                }
            }
        }
        return currentLowest;
    }

    private int calculateQuiescenceDepth(Transition moveTransition,
                                         int depth) {
        if (depth == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (moveTransition.getAfterMoveBoard().getCurrentPlayer().isInCheck()) {
                activityMeasure += 1;
            }
            for (AbstractMove move : BoardService.lastNMoves(moveTransition.getAfterMoveBoard(), 2)) {
                if (move.isAttackMove()) {
                    activityMeasure += 1;
                }
            }
            if (activityMeasure >= 2) {
                this.quiescenceCount++;
                return 1;
            }
        }
        return depth - 1;
    }

    private static String calculateTimeTaken(long start, long end) {
        long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }

}