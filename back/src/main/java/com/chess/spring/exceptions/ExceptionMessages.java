package com.chess.spring.exceptions;

public enum ExceptionMessages {
    //SYSTEM
    SYSTEM_ERROR_INVALID_DATA("Złe dane, system nie powinien się tutaj dostać"),
    NOT_EXPECTED_ERROR("Błąd nie rozpoznany"),

    //REGISTER
    REGISTER_INVALID_ACTIVATION_CODE("Nie poprawny od aktywacyjny"),
    REGISTER_ACCOUNT_ALREADY_ACTIVATED("Konto zostało już odblokowane"),

    //LOGIN
    LOGIN_ACOUNT_NOT_ACTIVATED("Konto nie zostało aktywowane"),

    //PROFILE
    PLAYER_NOT_FOUND("Taki gracz nie istnieje"),
    IMAGE_NOT_VALID("Błędny obraz"),
    IMAGE_TO_BIG("Avatar za duży"),
    AVATAR_NOT_SET("Avatar not set"),

    //INVITATIONS
    INVITATION_SELF_EXCEPTION("Nie można zaprosić samego siebie"),

    //GAME
    GAME_INVALID_MOVE("Błędny ruch!"),
    GAME_NOT_FOUND("Gra nie odnaleziona"),
    NOT_PLAYER_IN_THIS_GAME("Nie bierzesz udziału w tej grze"),
    GAME_END("Gra się zakończyła"),
    GAME_STATUS_NOT_VALID("Nie poprawny status gry"),
    GAME_LEVEL_NOT_VALID("Poziom gry poza skalą"),

    ;


    private String info;

    ExceptionMessages(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
