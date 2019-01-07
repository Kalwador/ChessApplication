package com.chess.spring.engine.core.analysers;

import com.chess.spring.engine.board.BoardService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class AnalyserService {
    private List<List<Boolean>> columns;

    public AnalyserService() {
        this.columns = initColumns();
    }

    public static List<List<Boolean>> initColumns() {
        List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardService.INSTANCE.FIRST_COLUMN);
        columns.add(BoardService.INSTANCE.SECOND_COLUMN);
        columns.add(BoardService.INSTANCE.THIRD_COLUMN);
        columns.add(BoardService.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardService.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardService.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardService.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardService.INSTANCE.EIGHTH_COLUMN);
        return columns;
    }
}
