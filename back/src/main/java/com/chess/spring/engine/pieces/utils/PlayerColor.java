package com.chess.spring.engine.pieces.utils;

import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.player.BlackPlayer;
import com.chess.spring.engine.player.WhitePlayer;

import static com.chess.spring.engine.core.PreferedStructure.*;

public enum PlayerColor {

    WHITE() {
        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getDirection() {
            return UP_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return DOWN_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardService.INSTANCE.FIRST_ROW.get(position);
        }

        @Override
        public AbstractPlayer choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString() {
            return "White";
        }

        @Override
        public int pawnBonus(int position) {
            return WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return WHITE_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(int position) {
            return WHITE_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(int position) {
            return WHITE_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(int position) {
            return WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(int position) {
            return WHITE_KING_PREFERRED_COORDINATES[position];
        }

    },
    BLACK() {
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getDirection() {
            return DOWN_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return UP_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardService.INSTANCE.EIGHTH_ROW.get(position);
        }

        @Override
        public AbstractPlayer choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public int pawnBonus(int position) {
            return BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return BLACK_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(int position) {
            return BLACK_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(int position) {
            return BLACK_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(int position) {
            return BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(int position) {
            return BLACK_KING_PREFERRED_COORDINATES[position];
        }
    },
    RANDOM() {
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getDirection() {
            return 0;
        }

        @Override
        public int getOppositeDirection() {
            return 0;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return false;
        }

        @Override
        public AbstractPlayer choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return null;
        }

        @Override
        public String toString() {
            return null;
        }

        @Override
        public int pawnBonus(int position) {
            return 0;
        }

        @Override
        public int knightBonus(int position) {
            return 0;
        }

        @Override
        public int bishopBonus(int position) {
            return 0;
        }

        @Override
        public int rookBonus(int position) {
            return 0;
        }

        @Override
        public int queenBonus(int position) {
            return 0;
        }

        @Override
        public int kingBonus(int position) {
            return 0;
        }
    };

    public abstract int getDirection();

    public abstract int getOppositeDirection();

    public abstract int pawnBonus(int position);

    public abstract int knightBonus(int position);

    public abstract int bishopBonus(int position);

    public abstract int rookBonus(int position);

    public abstract int queenBonus(int position);

    public abstract int kingBonus(int position);

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int position);

    public abstract AbstractPlayer choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

    private static int UP_DIRECTION = -1;

    private static int DOWN_DIRECTION = 1;

}