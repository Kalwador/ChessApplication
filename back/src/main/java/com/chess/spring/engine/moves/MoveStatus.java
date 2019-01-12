package com.chess.spring.engine.moves;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL {
        @Override
        public boolean isDone() {
            return false;
        }
    },
    CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();

}
