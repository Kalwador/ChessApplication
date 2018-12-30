package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.classic.player.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.Move;
import com.chess.spring.engine.moves.MoveFactory;
import com.chess.spring.engine.moves.Transition;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Collection;
import java.util.Comparator;
import java.util.Observable;

import static com.chess.spring.engine.board.BoardUtils.mvvlva;

public class StockAlphaBeta extends Observable implements MoveStrategy {

    private  BoardEvaluator evaluator;
    private  int searchDepth;
    private long boardsEvaluated;
    private long executionTime;
    private int quiescenceCount;
    private static  int MAX_QUIESCENCE = 5000*10;

    private enum MoveSorter {

        STANDARD {
            @Override
            Collection<Move> sort( Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        },
        EXPENSIVE {
            @Override
            Collection<Move> sort( Collection<Move> moves) {
                return Ordering.from((Comparator<Move>) (move1, move2) -> ComparisonChain.start()
                        .compareTrueFirst(BoardUtils.kingThreat(move1), BoardUtils.kingThreat(move2))
                        .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                        .compare(mvvlva(move2), mvvlva(move1))
                        .result()).immutableSortedCopy(moves);
            }
        };

        abstract  Collection<Move> sort(Collection<Move> moves);
    }


    public StockAlphaBeta( int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public String toString() {
        return "StockAlphaBeta";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute( Board board) {
         long startTime = System.currentTimeMillis();
         AbstractPlayer currentPlayer = board.currentPlayer();
        Move bestMove = MoveFactory.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
//        System.out.println(board.currentPlayer() + " THINKING with depth = " + this.searchDepth);
        int moveCounter = 1;
        int numMoves = board.currentPlayer().getLegalMoves().size();

        for ( Move move : MoveSorter.EXPENSIVE.sort((board.currentPlayer().getLegalMoves()))) {
             Transition moveTransition = board.currentPlayer().makeMove(move);
            this.quiescenceCount = 0;
             String s;
            if (moveTransition.getStatus().isDone()) {
                 long candidateMoveStartTime = System.nanoTime();
                currentValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getAfterMoveBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.getAfterMoveBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue);
                if (currentPlayer.getAlliance().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.getAfterMoveBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                }
                else if (currentPlayer.getAlliance().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    if(moveTransition.getAfterMoveBoard().whitePlayer().isInCheckMate()) {
                        break;
                    }
                }

                 String quiescenceInfo = " " + score(currentPlayer, highestSeenValue, lowestSeenValue) + " q: " +this.quiescenceCount;
                s = "\t" + toString() + "(" +this.searchDepth+ "), m: (" +moveCounter+ "/" +numMoves+ ") " + move + ", best:  " + bestMove

                        + quiescenceInfo + ", t: " +calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
            } else {
                s = "\t" + toString() + ", m: (" +moveCounter+ "/" +numMoves+ ") " + move + " is illegal! best: " +bestMove;
            }
//            System.out.println(s);
            setChanged();
            notifyObservers(s);
            moveCounter++;
        }

        this.executionTime = System.currentTimeMillis() - startTime;
         String result = board.currentPlayer() + " SELECTS " +bestMove+ " [#boards evaluated = " +this.boardsEvaluated+
                " time taken = " +this.executionTime/1000+ " rate = " +(1000 * ((double)this.boardsEvaluated/this.executionTime));
//        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, rate = %.1f\n", board.currentPlayer(),
//                bestMove, this.boardsEvaluated, this.executionTime, (1000 * ((double)this.boardsEvaluated/this.executionTime)));
        setChanged();
        notifyObservers(result);
        return bestMove;
    }

    private static String score( AbstractPlayer currentPlayer,
                                 int highestSeenValue,
                                 int lowestSeenValue) {

        if(currentPlayer.getAlliance().isWhite()) {
            return "[score: " +highestSeenValue + "]";
        } else if(currentPlayer.getAlliance().isBlack()) {
            return "[score: " +lowestSeenValue+ "]";
        }
        throw new RuntimeException("bad bad boy!");
    }

    private int max( Board board,
                     int depth,
                     int highest,
                     int lowest) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentHighest = highest;
        for ( Move move : MoveSorter.STANDARD.sort((board.currentPlayer().getLegalMoves()))) {
             Transition moveTransition = board.currentPlayer().makeMove(move);
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

    private int min( Board board,
                     int depth,
                     int highest,
                     int lowest) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentLowest = lowest;
        for ( Move move : MoveSorter.STANDARD.sort((board.currentPlayer().getLegalMoves()))) {
             Transition moveTransition = board.currentPlayer().makeMove(move);
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

    private int calculateQuiescenceDepth( Transition moveTransition,
                                          int depth) {
        if(depth == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (moveTransition.getAfterMoveBoard().currentPlayer().isInCheck()) {
                activityMeasure += 1;
            }
            for( Move move: BoardUtils.lastNMoves(moveTransition.getAfterMoveBoard(), 2)) {
                if(move.isAttack()) {
                    activityMeasure += 1;
                }
            }
            if(activityMeasure >= 2) {
                this.quiescenceCount++;
                return 1;
            }
        }
        return depth - 1;
    }

    private static String calculateTimeTaken( long start,  long end) {
         long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }

}