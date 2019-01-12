package com.chess.spring.engine.core.algorithms;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.core.evaluators.EvaluatorService;
import com.chess.spring.engine.core.evaluators.EvaluatorServiceImpl;
import com.chess.spring.engine.moves.ErrorMove;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.chess.spring.engine.moves.Transition;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Data
@Service
public class AlphaBetaAlgorithm extends AbstractAlgorithm {

    private EvaluatorService evaluator;
    private long boardsEvaluated;
    private long executionTime;
    private int quiescenceCount;
    private static int MAX_QUIESCENCE = 5000 * 10;

    public AlphaBetaAlgorithm(EvaluatorServiceImpl evaluatorService) {
        this.evaluator = evaluatorService;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public AbstractMove getBestMove(Board board, int level) {
        long startTime = System.currentTimeMillis();
        AbstractPlayer currentPlayer = board.getCurrentPlayer();
        AbstractMove bestMove = ErrorMove.getInstance();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        for (AbstractMove move : fullSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            String s;
            if (moveTransition.getStatus().isDone()) {
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
        }

        this.executionTime = System.currentTimeMillis() - startTime;
        return bestMove;
    }

    private int max(Board board, int depth, int highest, int min) {
        if (checkEndGame(board, depth)) return this.evaluator.evaluate(board, depth);
        int tempMax = highest;
        for (AbstractMove move : simpleSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                tempMax = Math.max(tempMax, min(moveTransition.getAfterMoveBoard(),
                        calculateQuiescenceDepth(moveTransition, depth), tempMax, min));
                if (tempMax >= min) {
                    return min;
                }
            }
        }
        return tempMax;
    }

    private int min(Board board,
                    int depth,
                    int max,
                    int lowest) {
        if (checkEndGame(board, depth)) return this.evaluator.evaluate(board, depth);
        int tempMin = lowest;
        for (AbstractMove move : simpleSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                tempMin = Math.min(tempMin, max(moveTransition.getAfterMoveBoard(),
                        calculateQuiescenceDepth(moveTransition, depth), max, tempMin));
                if (tempMin <= max) {
                    return max;
                }
            }
        }
        return tempMin;
    }

    private boolean checkEndGame(Board board, int depth) {
        if (depth == 0 || BoardService.isEndGame(board)) {
            this.boardsEvaluated++;
            return true;
        }
        return false;
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

}