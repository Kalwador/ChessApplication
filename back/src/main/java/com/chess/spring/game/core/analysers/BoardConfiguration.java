package com.chess.spring.game.core.analysers;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class BoardConfiguration {
    private static BoardConfiguration instance;

    private List<List<Boolean>> columns;
    public static final int START_TILE_INDEX = 0;
    public static final int TILES_PER_ROW = 8;
    public static final int TILES_MAX = 64;


    public final List<Boolean> FIRST_COLUMN = initColumn(0);
    public final List<Boolean> SECOND_COLUMN = initColumn(1);
    public final List<Boolean> THIRD_COLUMN = initColumn(2);
    public final List<Boolean> FOURTH_COLUMN = initColumn(3);
    public final List<Boolean> FIFTH_COLUMN = initColumn(4);
    public final List<Boolean> SIXTH_COLUMN = initColumn(5);
    public final List<Boolean> SEVENTH_COLUMN = initColumn(6);
    public final List<Boolean> EIGHTH_COLUMN = initColumn(7);
    public final List<Boolean> FIRST_ROW = initRow(0);
    public final List<Boolean> SECOND_ROW = initRow(8);
    public final List<Boolean> THIRD_ROW = initRow(16);
    public final List<Boolean> FOURTH_ROW = initRow(24);
    public final List<Boolean> FIFTH_ROW = initRow(32);
    public final List<Boolean> SIXTH_ROW = initRow(40);
    public final List<Boolean> SEVENTH_ROW = initRow(48);
    public final List<Boolean> EIGHTH_ROW = initRow(56);


    private BoardConfiguration() {
        this.columns = initColumns();
    }

    public static BoardConfiguration getInstance() {
        if(instance == null){
            instance = new BoardConfiguration();
        }
        return instance;
    }


    public List<List<Boolean>> initColumns() {
        return Arrays.asList(
                FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN, FOURTH_COLUMN,
                FIFTH_COLUMN, SIXTH_COLUMN, SEVENTH_COLUMN, EIGHTH_COLUMN);
    }

    private static List<Boolean> initColumn(int columnNumber) {
        final Boolean[] column = new Boolean[TILES_MAX];
        for (int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += TILES_PER_ROW;
        } while (columnNumber < TILES_MAX);
        return Arrays.asList(column);
    }

    private static List<Boolean> initRow(int rowNumber) {
        final Boolean[] row = new Boolean[TILES_MAX];
        for (int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % TILES_PER_ROW != 0);
        return Arrays.asList(row);
    }
}
