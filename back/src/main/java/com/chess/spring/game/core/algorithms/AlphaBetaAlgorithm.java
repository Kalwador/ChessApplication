package com.chess.spring.game.core.algorithms;

import com.chess.spring.game.board.Board;
import com.chess.spring.game.board.BoardService;
import com.chess.spring.game.core.evaluators.EvaluatorService;
import com.chess.spring.game.core.evaluators.EvaluatorServiceImpl;
import com.chess.spring.game.moves.ErrorMove;
import com.chess.spring.game.player.AbstractPlayer;
import com.chess.spring.game.moves.simple.AbstractMove;
import com.chess.spring.game.moves.Transition;
import lombok.Data;
import org.springframework.stereotype.Service;

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
        int alfa = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int tempValue;
        for (AbstractMove move : fullSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            String s;
            if (moveTransition.getStatus().isDone()) {
                tempValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getAfterMoveBoard(), level - 1, alfa, beta) :
                        max(moveTransition.getAfterMoveBoard(), level - 1, alfa, beta);
                if (currentPlayer.getAlliance().isWhite() && tempValue > alfa) {
                    alfa = tempValue;
                    bestMove = move;
                    if (moveTransition.getAfterMoveBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                } else if (currentPlayer.getAlliance().isBlack() && tempValue < beta) {
                    beta = tempValue;
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

    private int max(Board board, int level, int max, int min) {
        if (checkEndGame(board, level)) return this.evaluator.evaluate(board, level);
        for (AbstractMove move : simpleSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                max = Math.max(max, min(moveTransition.getAfterMoveBoard(),
                        getAverageLevel(moveTransition, level), max, min));
                if (max >= min) {
                    return min;
                }
            }
        }
        return max;
    }

    private int min(Board board, int level, int max, int min) {
        if (checkEndGame(board, level)) return this.evaluator.evaluate(board, level);
        for (AbstractMove move : simpleSort((board.getCurrentPlayer().getLegalMoves()))) {
            Transition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getStatus().isDone()) {
                min = Math.min(min, max(moveTransition.getAfterMoveBoard(),
                        getAverageLevel(moveTransition, level), max, min));
                if (min <= max) {
                    return max;
                }
            }
        }
        return min;
    }

    private boolean checkEndGame(Board board, int level) {
        if (level == 0 || BoardService.isEndGame(board)) {
            this.boardsEvaluated++;
            return true;
        }
        return false;
    }

    private int getAverageLevel(Transition transition, int level) {
        if (level == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (transition.getAfterMoveBoard().getCurrentPlayer().isInCheck()) {
                activityMeasure += 1;
            }
            for (AbstractMove move : BoardService.lastNMoves(transition.getAfterMoveBoard(), 2)) {
                if (move.isAttackMove()) {
                    activityMeasure += 1;
                }
            }
            if (activityMeasure >= 2) {
                this.quiescenceCount++;
                return 1;
            }
        }
        return level - 1;
    }

}