package com.chess.spring.engine.core.algorithms;

import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.moves.simple.AbstractMove;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Collection;
import java.util.Comparator;

import static com.chess.spring.engine.board.BoardService.calculatePieceExchangeValue;

public enum MoveSorter {

    STANDARD {
        @Override
        public Collection<AbstractMove> sort(Collection<AbstractMove> moves) {
            return Ordering.from((Comparator<AbstractMove>) (move1, move2) -> ComparisonChain.start()
                    .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                    .compare(calculatePieceExchangeValue(move2), calculatePieceExchangeValue(move1))
                    .result()).immutableSortedCopy(moves);
        }
    },
    EXPENSIVE {
        @Override
        public Collection<AbstractMove> sort(Collection<AbstractMove> moves) {
            return Ordering.from((Comparator<AbstractMove>) (move1, move2) -> ComparisonChain.start()
                    .compareTrueFirst(BoardService.kingThreat(move1), BoardService.kingThreat(move2))
                    .compareTrueFirst(move1.isCastlingMove(), move2.isCastlingMove())
                    .compare(calculatePieceExchangeValue(move2), calculatePieceExchangeValue(move1))
                    .result()).immutableSortedCopy(moves);
        }
    };

    public abstract Collection<AbstractMove> sort(Collection<AbstractMove> moves);
}
