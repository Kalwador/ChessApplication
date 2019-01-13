package com.chess.spring.game.pieces.utils;

public enum PieceType {

    PAWN(100, "P") {
        @Override
        public boolean isPawn() {
            return true;
        }

        @Override
        public boolean isBishop() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }

        @Override
        public boolean isKing() {
            return false;
        }
    },
    KNIGHT(320, "N") {
        @Override
        public boolean isPawn() {
            return false;
        }

        @Override
        public boolean isBishop() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }

        @Override
        public boolean isKing() {
            return false;
        }
    },
    BISHOP(350, "B") {
        @Override
        public boolean isPawn() {
            return false;
        }

        @Override
        public boolean isBishop() {
            return true;
        }

        @Override
        public boolean isRook() {
            return false;
        }

        @Override
        public boolean isKing() {
            return false;
        }
    },
    ROOK(500, "R") {
        @Override
        public boolean isPawn() {
            return false;
        }

        @Override
        public boolean isBishop() {
            return false;
        }

        @Override
        public boolean isRook() {
            return true;
        }

        @Override
        public boolean isKing() {
            return false;
        }
    },
    QUEEN(900, "Q") {
        @Override
        public boolean isPawn() {
            return false;
        }

        @Override
        public boolean isBishop() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }

        @Override
        public boolean isKing() {
            return false;
        }
    },
    KING(20000, "K") {
        @Override
        public boolean isPawn() {
            return false;
        }

        @Override
        public boolean isBishop() {
            return false;
        }

        @Override
        public boolean isRook() {
            return false;
        }

        @Override
        public boolean isKing() {
            return true;
        }
    };

    private int value;
    private String pieceName;

    public int getPieceValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }

    PieceType(int val, String pieceName) {
        this.value = val;
        this.pieceName = pieceName;
    }

    public abstract boolean isPawn();

    public abstract boolean isBishop();

    public abstract boolean isRook();

    public abstract boolean isKing();

}
