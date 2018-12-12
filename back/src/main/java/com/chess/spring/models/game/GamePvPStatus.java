package com.chess.spring.models.game;

public enum GamePvPStatus {
    ROOM(""),
    BLACK_MOVE(""),
    WHITE_MOVE(""),
    DRAW(""),
    WHITE_WIN("Wygrały białe"),
    BLACK_WIN("Wygrały czarne");


    private final String info;

    GamePvPStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return this.info;
    }
}
