package com.chess.spring.exceptions;

public enum ExceptionMessages {
    GAME_INVALID_MOVE("Błędny ruch!"),
    GAME_NOT_FOUND("Gra nie odnaleziona"),
    NOT_PLAYER_IN_THIS_GAME("Nie bierzesz udziału w tej grze"),
    GAME_END("Gra się zakończyła"),
    GAME_STATUS_NOT_VALID("Nie poprawny status gry"),
    GAME_LEVEL_NOT_VALID("Poziom gry poza skalą"),

    NOT_EXPECTED_ERROR("Błąd nie rozpoznany")
    ;


    private String info;

    ExceptionMessages(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
