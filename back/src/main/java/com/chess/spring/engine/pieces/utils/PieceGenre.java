package com.chess.spring.engine.pieces.utils;

import com.chess.spring.engine.board.PieceConfiguration;
import com.chess.spring.engine.board.BoardConfiguration;
import com.chess.spring.engine.moves.simple.Move;

import java.util.ArrayList;
import java.util.List;

public enum PieceGenre {

    WHITE_KNIGHTS() {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateKnightLegals(PieceConfiguration.whiteKnights);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.whiteLegalLocations.set(moveLocation);
        }

    },

    WHITE_BISHOPS {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateBishopLegals(PieceConfiguration.whiteBishops);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.whiteLegalLocations.set(moveLocation);
        }
    },
    WHITE_ROOKS {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateRookLegals(PieceConfiguration.whiteRooks);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.whiteLegalLocations.set(moveLocation);
        }
    },
    WHITE_PAWNS {
        @Override
        public List<Move> calculateLegalMoves() {

            List<Move> legalMoveImplementations = new ArrayList<>();
            BoardConfiguration allPieces = allPieces();
            BoardConfiguration enemyPieces = enemyPieces();
            BoardConfiguration pawnAdvances = new BoardConfiguration(PieceConfiguration.whitePawns);
            pawnAdvances.shift(-8);
            pawnAdvances.andNot(allPieces);
            BoardConfiguration pawnJumps = new BoardConfiguration(PieceConfiguration.whitePawns);
            pawnJumps.shift(-16);
            pawnJumps.andNot(allPieces);
            BoardConfiguration pawnAttacksLeft = new BoardConfiguration(PieceConfiguration.whitePawns);
            pawnAttacksLeft.shift(-9);
            pawnAttacksLeft.and(enemyPieces);
            BoardConfiguration pawnAttacksRight = new BoardConfiguration(PieceConfiguration.whitePawns);
            pawnAttacksRight.shift(-7);
            pawnAttacksRight.and(enemyPieces);

            for (int currentPawnLocation = PieceConfiguration.whitePawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = PieceConfiguration.whitePawns
                    .nextSetBit(currentPawnLocation + 1)) {

                int candidateLocation = currentPawnLocation - 8;

                if (pawnAdvances.get(candidateLocation)) {
                    legalMoveImplementations.add(new Move(currentPawnLocation,
                            candidateLocation, this));
                }

                candidateLocation = currentPawnLocation - 16;

                if (pawnJumps.get(candidateLocation)) {
                    legalMoveImplementations.add(new Move(currentPawnLocation,
                            candidateLocation, this));
                }

            }

            return legalMoveImplementations;
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.whiteLegalLocations.set(moveLocation);
        }
    },
    BLACK_KNIGHTS() {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateKnightLegals(PieceConfiguration.blackKnights);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.blackLegalLocations.set(moveLocation);
        }

    },
    BLACK_BISHOPS {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateBishopLegals(PieceConfiguration.blackBishops);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.blackLegalLocations.set(moveLocation);
        }
    },
    BLACK_ROOKS {
        @Override
        public List<Move> calculateLegalMoves() {
            return calculateRookLegals(PieceConfiguration.blackRooks);
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.blackLegalLocations.set(moveLocation);
        }
    },
    BLACK_PAWNS {
        @Override
        public List<Move> calculateLegalMoves() {

            List<Move> legalMoveImplementations = new ArrayList<>();
            BoardConfiguration allPieces = allPieces();
            BoardConfiguration enemyPieces = enemyPieces();
            BoardConfiguration pawnAdvances = new BoardConfiguration(PieceConfiguration.blackPawns);
            pawnAdvances.shift(8);
            pawnAdvances.andNot(allPieces);
            BoardConfiguration pawnJumps = new BoardConfiguration(PieceConfiguration.blackPawns);
            pawnJumps.shift(16);
            pawnJumps.andNot(allPieces);
            BoardConfiguration pawnAttacksLeft = new BoardConfiguration(PieceConfiguration.blackPawns);
            pawnAttacksLeft.shift(9);
            pawnAttacksLeft.and(enemyPieces);
            BoardConfiguration pawnAttacksRight = new BoardConfiguration(PieceConfiguration.blackPawns);
            pawnAttacksRight.shift(7);
            pawnAttacksRight.and(enemyPieces);

            for (int currentPawnLocation = PieceConfiguration.blackPawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = PieceConfiguration.blackPawns
                    .nextSetBit(currentPawnLocation + 1)) {

                int candidateLocation = currentPawnLocation + 8;

                if (pawnAdvances.get(candidateLocation)) {
                    legalMoveImplementations.add(new Move(currentPawnLocation,
                            candidateLocation, this));
                }

                candidateLocation = currentPawnLocation + 16;

                if (pawnJumps.get(candidateLocation)) {
                    legalMoveImplementations.add(new Move(currentPawnLocation,
                            candidateLocation, this));
                }

            }

            PieceConfiguration.whiteLegalLocations.or(pawnAdvances);
            PieceConfiguration.whiteLegalLocations.or(pawnJumps);

            return legalMoveImplementations;
        }

        @Override
        public BoardConfiguration alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public BoardConfiguration enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(int moveLocation) {
            PieceConfiguration.blackLegalLocations.set(moveLocation);
        }
    };

    public abstract List<Move> calculateLegalMoves();

    public abstract BoardConfiguration alliedPieces();

    public abstract BoardConfiguration enemyPieces();

    public abstract void setBits(int moveLocation);

    public boolean isOccupied(int position) {
        return allPieces().get(position);
    }

    public List<Move> calculateKingLegals(BoardConfiguration kingBitSet) {

        BoardConfiguration alliedUnits = alliedPieces();
        int kingPos = kingBitSet.nextSetBit(0);
        int moveLocation = kingPos - 1;
        List<Move> legalMoveImplementations = new ArrayList<>();

        if (alliedUnits.get(kingPos) && !PieceConfiguration.FILE_A.get(kingPos)) {
            if (isTileValid(moveLocation)) {
                setBits(moveLocation);
            }
        }

        moveLocation = kingPos + 1;

        if (!alliedUnits.get(kingPos) && !PieceConfiguration.FILE_H.get(kingPos)) {
            if (isTileValid(moveLocation)) {
                setBits(moveLocation);
            }
        }

        moveLocation = kingPos + 8;

        if (isTileValid(moveLocation) && !alliedUnits.get(kingPos)
                && (PieceConfiguration.FULL_SET.get(moveLocation))) {
            setBits(moveLocation);
        }

        moveLocation = kingPos - 8;

        if (isTileValid(moveLocation) && !alliedUnits.get(kingPos)
                && (PieceConfiguration.FULL_SET.get(moveLocation))) {
            setBits(moveLocation);
        }

        return legalMoveImplementations;
    }

    private static boolean isTileValid(int moveLocation) {
        return moveLocation >= 0 && moveLocation < 64;
    }


    public List<Move> calculateRookLegals(BoardConfiguration rookBitSet) {

        BoardConfiguration alliedUnits = alliedPieces();
        BoardConfiguration enemyUnits = enemyPieces();
        List<Move> legalMoveImplementations = new ArrayList<>();

        for (int currentRookLocation = rookBitSet.nextSetBit(0); currentRookLocation >= 0; currentRookLocation = rookBitSet
                .nextSetBit(currentRookLocation + 1)) {

            BoardConfiguration vertical = PieceConfiguration.ALL_FILES.get(currentRookLocation);
            BoardConfiguration horizontal = PieceConfiguration.ALL_RANKS
                    .get(currentRookLocation);

            // up
            int candidateLocation = currentRookLocation - 8;
            int endPos = vertical.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentRookLocation, candidateLocation))
                    break;
                candidateLocation -= 8;
            }

            // down
            candidateLocation = currentRookLocation + 8;
            endPos = vertical.length();
            while (endPos >= candidateLocation) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentRookLocation, candidateLocation))
                    break;
                candidateLocation += 8;
            }

            // left
            candidateLocation = currentRookLocation - 1;
            endPos = horizontal.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentRookLocation, candidateLocation))
                    break;
                candidateLocation--;
            }

            // right
            candidateLocation = currentRookLocation + 1;
            endPos = horizontal.length() - 1;
            while (candidateLocation <= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentRookLocation, candidateLocation))
                    break;
                candidateLocation++;
            }

        }

        return legalMoveImplementations;

    }

    public List<Move> calculateBishopLegals(BoardConfiguration bishopBitSet) {

        BoardConfiguration alliedUnits = alliedPieces();
        BoardConfiguration enemyUnits = enemyPieces();
        List<Move> legalMoveImplementations = new ArrayList<>();

        for (int currentBishopLocation = bishopBitSet.nextSetBit(0); currentBishopLocation >= 0; currentBishopLocation = bishopBitSet
                .nextSetBit(currentBishopLocation + 1)) {

            BoardConfiguration rightDiag = PieceConfiguration.ALL_RIGHT_DIAGONALS
                    .get(currentBishopLocation);
            BoardConfiguration leftDiag = PieceConfiguration.ALL_LEFT_DIAGONALS
                    .get(currentBishopLocation);

            int candidateLocation = currentBishopLocation - 7;
            int endPos = rightDiag.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentBishopLocation, candidateLocation))
                    break;
                candidateLocation -= 7;
            }

            candidateLocation = currentBishopLocation + 7;
            endPos = rightDiag.length() - 1;
            while (endPos >= candidateLocation) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentBishopLocation, candidateLocation))
                    break;
                candidateLocation += 7;
            }

            candidateLocation = currentBishopLocation - 9;
            endPos = leftDiag.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentBishopLocation, candidateLocation))
                    break;
                candidateLocation -= 9;
            }

            candidateLocation = currentBishopLocation + 9;
            endPos = leftDiag.length() - 1;
            while (candidateLocation <= endPos) {
                if (setLegalImplementations(alliedUnits, enemyUnits, legalMoveImplementations, currentBishopLocation, candidateLocation))
                    break;
                candidateLocation += 9;
            }

        }

        return legalMoveImplementations;
    }

    private boolean setLegalImplementations(BoardConfiguration alliedUnits, BoardConfiguration enemyUnits, List<Move> legalMoveImplementations, int currentBishopLocation, int candidateLocation) {
        if (alliedUnits.get(candidateLocation)) {
            return true;
        }
        setBits(candidateLocation);
        legalMoveImplementations.add(new Move(currentBishopLocation,
                candidateLocation, this));
        if (enemyUnits.get(candidateLocation)) {
            return true;
        }
        return false;
    }

    public List<Move> calculateKnightLegals(BoardConfiguration knightBitSet) {

        BoardConfiguration alliedUnits = alliedPieces();
        BoardConfiguration enemyUnits = enemyPieces();
        List<Move> legalMoveImplementations = new ArrayList<>();

        for (int currentKnightLocation = knightBitSet.nextSetBit(0); currentKnightLocation >= 0; currentKnightLocation = knightBitSet
                .nextSetBit(currentKnightLocation + 1)) {
            if (!(PieceConfiguration.FILE_G.get(currentKnightLocation) || PieceConfiguration.FILE_H
                    .get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation - 6;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_A.get(currentKnightLocation) || PieceConfiguration.FILE_B
                    .get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation - 10;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_H.get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation - 15;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_A.get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation - 17;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_A.get(currentKnightLocation) || PieceConfiguration.FILE_B
                    .get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation + 6;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_G.get(currentKnightLocation) || PieceConfiguration.FILE_H
                    .get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation + 10;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_A.get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation + 15;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
            if (!(PieceConfiguration.FILE_H.get(currentKnightLocation))) {
                int candidateLocation = currentKnightLocation + 17;
                setLegalMoves(alliedUnits, enemyUnits, legalMoveImplementations, currentKnightLocation, candidateLocation);
            }
        }

        return legalMoveImplementations;

    }

    private void setLegalMoves(BoardConfiguration alliedUnits, BoardConfiguration enemyUnits, List<Move> legalMoveImplementations, int currentKnightLocation, int candidateLocation) {
        if (isTileValid(candidateLocation)) {
            if (!alliedUnits.get(candidateLocation)
                    || enemyUnits.get(candidateLocation)) {
                setBits(candidateLocation);
                legalMoveImplementations.add(new Move(currentKnightLocation,
                        candidateLocation, this));
            }
        }
    }

    public static BoardConfiguration allPawns() {
        BoardConfiguration allPawns = new BoardConfiguration();
        allPawns.or(PieceConfiguration.whitePawns);
        allPawns.or(PieceConfiguration.blackPawns);
        return allPawns;
    }

    public static BoardConfiguration allKnights() {
        BoardConfiguration allKnights = new BoardConfiguration();
        allKnights.or(PieceConfiguration.whiteKnights);
        allKnights.or(PieceConfiguration.blackKnights);
        return allKnights;
    }

    public static BoardConfiguration allBishops() {
        BoardConfiguration allBishops = new BoardConfiguration();
        allBishops.or(PieceConfiguration.whiteBishops);
        allBishops.or(PieceConfiguration.blackBishops);
        return allBishops;
    }

    public static BoardConfiguration allRooks() {
        BoardConfiguration allRooks = new BoardConfiguration();
        allRooks.or(PieceConfiguration.whiteRooks);
        allRooks.or(PieceConfiguration.blackRooks);
        return allRooks;
    }

    public static BoardConfiguration allQueens() {
        BoardConfiguration allQueens = new BoardConfiguration();
        allQueens.or(PieceConfiguration.whiteQueens);
        allQueens.or(PieceConfiguration.blackQueens);
        return allQueens;
    }

    public static BoardConfiguration allKings() {
        BoardConfiguration allKings = new BoardConfiguration();
        allKings.or(PieceConfiguration.whiteKing);
        allKings.or(PieceConfiguration.blackKing);
        return allKings;
    }

    public static BoardConfiguration allPieces() {
        BoardConfiguration allPieces = new BoardConfiguration();
        allPieces.or(PieceConfiguration.whiteRooks);
        allPieces.or(PieceConfiguration.whiteKnights);
        allPieces.or(PieceConfiguration.whiteBishops);
        allPieces.or(PieceConfiguration.whiteQueens);
        allPieces.or(PieceConfiguration.whitePawns);
        return setDefaultsBlackPieces(allPieces);
    }

    public static BoardConfiguration allWhitePieces() {
        BoardConfiguration allWhitePieces = new BoardConfiguration();
        allWhitePieces.or(PieceConfiguration.whiteRooks);
        allWhitePieces.or(PieceConfiguration.whiteKnights);
        allWhitePieces.or(PieceConfiguration.whiteBishops);
        allWhitePieces.or(PieceConfiguration.whiteQueens);
        allWhitePieces.or(PieceConfiguration.whitePawns);
        return allWhitePieces;
    }

    public static BoardConfiguration allBlackPieces() {
        BoardConfiguration allBlackPieces = new BoardConfiguration();
        return setDefaultsBlackPieces(allBlackPieces);
    }

    private static BoardConfiguration setDefaultsBlackPieces(BoardConfiguration allBlackPieces) {
        allBlackPieces.or(PieceConfiguration.blackRooks);
        allBlackPieces.or(PieceConfiguration.blackKnights);
        allBlackPieces.or(PieceConfiguration.blackBishops);
        allBlackPieces.or(PieceConfiguration.blackQueens);
        allBlackPieces.or(PieceConfiguration.blackPawns);
        return allBlackPieces;
    }

}