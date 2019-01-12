package com.chess.spring.engine.core.algorithms;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.List;

import static com.chess.spring.engine.board.BoardService.calculatePieceExchangeValue;

public abstract class AbstractAlgorithm {
    public abstract AbstractMove getBestMove(Board board, int level);

    protected List<AbstractMove> simpleSort(List<AbstractMove> moves) {
        return Ordering.from((Comparator<AbstractMove>) (move1, move2) -> ComparisonChain.start()
                .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                .compare(calculatePieceExchangeValue(move2), calculatePieceExchangeValue(move1))
                .result()).immutableSortedCopy(moves);
    }

    public List<AbstractMove> fullSort(List<AbstractMove> moves) {
        return Ordering.from((Comparator<AbstractMove>) (move1, move2) -> ComparisonChain.start()
                .compareTrueFirst(BoardService.kingThreat(move1), BoardService.kingThreat(move2))
                .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                .compare(calculatePieceExchangeValue(move2), calculatePieceExchangeValue(move1))
                .result()).immutableSortedCopy(moves);
    }
}

