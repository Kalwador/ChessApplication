package com.chess.spring.game.pieces.utils;

import com.chess.spring.game.GameService;
import com.chess.spring.game.core.analysers.BoardConfiguration;
import com.chess.spring.game.pieces.*;
import com.chess.spring.game.player.AbstractPlayer;
import com.chess.spring.game.player.BlackPlayer;
import com.chess.spring.game.player.WhitePlayer;


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
            return GameService.UP_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return GameService.DOWN_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardConfiguration.getInstance().FIRST_ROW.get(position);
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
            return Pawn.WHITE_BONUS_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return Knight.WHITE_BONUS_COORDINATES[position];
        }

        @Override
        public int bishopBonus(int position) {
            return Bishop.WHITE_BONUS_COORDINATES[position];
        }

        @Override
        public int rookBonus(int position) {
            return Rook.WHITE_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(int position) {
            return Queen.WHITE_BONUS_COORDINATES[position];
        }

        @Override
        public int kingBonus(int position) {
            return King.WHITE_BONUS_COORDINATES[position];
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
            return GameService.DOWN_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return GameService.UP_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardConfiguration.getInstance().EIGHTH_ROW.get(position);
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
            return Pawn.BLACK_BONUS_COORDINATES[position];
        }

        @Override
        public int knightBonus(int position) {
            return Knight.BLACK_BONUS_COORDINATES[position];
        }

        @Override
        public int bishopBonus(int position) {
            return Bishop.BLACK_BONUS_COORDINATES[position];
        }

        @Override
        public int rookBonus(int position) {
            return Rook.BLACK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(int position) {
            return Queen.BLACK_BONUS_COORDINATES[position];
        }

        @Override
        public int kingBonus(int position) {
            return King.BLACK_BONUS_COORDINATES[position];
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

}