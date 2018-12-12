package com.chess.spring.engine.pieces;

import com.chess.spring.engine.board.BitBoard;
import com.chess.spring.engine.board.ChessBitSet;
import com.chess.spring.engine.move.MoveImplementation;

import java.util.ArrayList;
import java.util.List;

public enum PieceType {

    WHITE_KNIGHTS() {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateKnightLegals(BitBoard.whiteKnights);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.whiteLegalLocations.set(moveLocation);
        }

    },

    WHITE_BISHOPS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateBishopLegals(BitBoard.whiteBishops);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.whiteLegalLocations.set(moveLocation);
        }
    },
    WHITE_ROOKS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateRookLegals(BitBoard.whiteRooks);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.whiteLegalLocations.set(moveLocation);
        }
    },
    WHITE_PAWNS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {

            final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();
            final ChessBitSet allPieces = allPieces();
            final ChessBitSet enemyPieces = enemyPieces();
            final ChessBitSet pawnAdvances = new ChessBitSet(BitBoard.whitePawns);
            pawnAdvances.shift(-8);
            pawnAdvances.andNot(allPieces);
            final ChessBitSet pawnJumps = new ChessBitSet(BitBoard.whitePawns);
            pawnJumps.shift(-16);
            pawnJumps.andNot(allPieces);
            final ChessBitSet pawnAttacksLeft = new ChessBitSet(BitBoard.whitePawns);
            pawnAttacksLeft.shift(-9);
            pawnAttacksLeft.and(enemyPieces);
            final ChessBitSet pawnAttacksRight = new ChessBitSet(BitBoard.whitePawns);
            pawnAttacksRight.shift(-7);
            pawnAttacksRight.and(enemyPieces);

            for (int currentPawnLocation = BitBoard.whitePawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = BitBoard.whitePawns
                    .nextSetBit(currentPawnLocation + 1)) {

                int candidateLocation = currentPawnLocation - 8;

                if (pawnAdvances.get(candidateLocation)) {
                    legalMoveImplementations.add(new MoveImplementation(currentPawnLocation,
                            candidateLocation, this));
                }

                candidateLocation = currentPawnLocation - 16;

                if (pawnJumps.get(candidateLocation)) {
                    legalMoveImplementations.add(new MoveImplementation(currentPawnLocation,
                            candidateLocation, this));
                }

            }

            return legalMoveImplementations;
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allWhitePieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allBlackPieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.whiteLegalLocations.set(moveLocation);
        }
    },
    BLACK_KNIGHTS() {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateKnightLegals(BitBoard.blackKnights);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }

    },
    BLACK_BISHOPS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateBishopLegals(BitBoard.blackBishops);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }
    },
    BLACK_ROOKS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {
            return calculateRookLegals(BitBoard.blackRooks);
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }
    },
    BLACK_PAWNS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {

            final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();
            final ChessBitSet allPieces = allPieces();
            final ChessBitSet enemyPieces = enemyPieces();
            final ChessBitSet pawnAdvances = new ChessBitSet(BitBoard.blackPawns);
            pawnAdvances.shift(8);
            pawnAdvances.andNot(allPieces);
            final ChessBitSet pawnJumps = new ChessBitSet(BitBoard.blackPawns);
            pawnJumps.shift(16);
            pawnJumps.andNot(allPieces);
            final ChessBitSet pawnAttacksLeft = new ChessBitSet(BitBoard.blackPawns);
            pawnAttacksLeft.shift(9);
            pawnAttacksLeft.and(enemyPieces);
            final ChessBitSet pawnAttacksRight = new ChessBitSet(BitBoard.blackPawns);
            pawnAttacksRight.shift(7);
            pawnAttacksRight.and(enemyPieces);

            for (int currentPawnLocation = BitBoard.blackPawns.nextSetBit(0); currentPawnLocation >= 0; currentPawnLocation = BitBoard.blackPawns
                    .nextSetBit(currentPawnLocation + 1)) {

                int candidateLocation = currentPawnLocation + 8;

                if (pawnAdvances.get(candidateLocation)) {
                    legalMoveImplementations.add(new MoveImplementation(currentPawnLocation,
                            candidateLocation, this));
                }

                candidateLocation = currentPawnLocation + 16;

                if (pawnJumps.get(candidateLocation)) {
                    legalMoveImplementations.add(new MoveImplementation(currentPawnLocation,
                            candidateLocation, this));
                }

            }

            BitBoard.whiteLegalLocations.or(pawnAdvances);
            BitBoard.whiteLegalLocations.or(pawnJumps);

            return legalMoveImplementations;
        }

        @Override
        public ChessBitSet alliedPieces() {
            return allBlackPieces();
        }

        @Override
        public ChessBitSet enemyPieces() {
            return allWhitePieces();
        }

        @Override
        public void setBits(final int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }
    };

    public abstract List<MoveImplementation> calculateLegalMoves();

    public abstract ChessBitSet alliedPieces();

    public abstract ChessBitSet enemyPieces();

    public abstract void setBits(int moveLocation);

    public boolean isOccupied(final int position) {
        return allPieces().get(position);
    }

    public List<MoveImplementation> calculateKingLegals(final ChessBitSet kingBitSet) {

        final ChessBitSet alliedUnits = alliedPieces();
        final int kingPos = kingBitSet.nextSetBit(0);
        int moveLocation = kingPos - 1;
        final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        if (alliedUnits.get(kingPos) && !BitBoard.FILE_A.get(kingPos)) {
            if (isTileValid(moveLocation)) {
                setBits(moveLocation);
            }
        }

        moveLocation = kingPos + 1;

        if (!alliedUnits.get(kingPos) && !BitBoard.FILE_H.get(kingPos)) {
            if (isTileValid(moveLocation)) {
                setBits(moveLocation);
            }
        }

        moveLocation = kingPos + 8;

        if (isTileValid(moveLocation) && !alliedUnits.get(kingPos)
                && (BitBoard.FULL_SET.get(moveLocation))) {
            setBits(moveLocation);
        }

        moveLocation = kingPos - 8;

        if (isTileValid(moveLocation) && !alliedUnits.get(kingPos)
                && (BitBoard.FULL_SET.get(moveLocation))) {
            setBits(moveLocation);
        }

        return legalMoveImplementations;
    }

    private static boolean isTileValid(final int moveLocation) {
        return moveLocation >= 0 && moveLocation < 64;
    }


    public List<MoveImplementation> calculateRookLegals(final ChessBitSet rookBitSet) {

        final ChessBitSet alliedUnits = alliedPieces();
        final ChessBitSet enemyUnits = enemyPieces();
        final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentRookLocation = rookBitSet.nextSetBit(0); currentRookLocation >= 0; currentRookLocation = rookBitSet
                .nextSetBit(currentRookLocation + 1)) {

            final ChessBitSet vertical = BitBoard.ALL_FILES.get(currentRookLocation);
            final ChessBitSet horizontal = BitBoard.ALL_RANKS
                    .get(currentRookLocation);

            // up
            int candidateLocation = currentRookLocation - 8;
            int endPos = vertical.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentRookLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation -= 8;
            }

            // down
            candidateLocation = currentRookLocation + 8;
            endPos = vertical.length();
            while (endPos >= candidateLocation) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentRookLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation += 8;
            }

            // left
            candidateLocation = currentRookLocation - 1;
            endPos = horizontal.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentRookLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation--;
            }

            // right
            candidateLocation = currentRookLocation + 1;
            endPos = horizontal.length() - 1;
            while (candidateLocation <= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentRookLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation++;
            }

        }

        return legalMoveImplementations;

    }

    public List<MoveImplementation> calculateBishopLegals(final ChessBitSet bishopBitSet) {

        final ChessBitSet alliedUnits = alliedPieces();
        final ChessBitSet enemyUnits = enemyPieces();
        final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentBishopLocation = bishopBitSet.nextSetBit(0); currentBishopLocation >= 0; currentBishopLocation = bishopBitSet
                .nextSetBit(currentBishopLocation + 1)) {

            final ChessBitSet rightDiag = BitBoard.ALL_RIGHT_DIAGONALS
                    .get(currentBishopLocation);
            final ChessBitSet leftDiag = BitBoard.ALL_LEFT_DIAGONALS
                    .get(currentBishopLocation);

            // up and to the right
            int candidateLocation = currentBishopLocation - 7;
            int endPos = rightDiag.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentBishopLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation -= 7;
            }

            // down and to the left
            candidateLocation = currentBishopLocation + 7;
            endPos = rightDiag.length() - 1;
            while (endPos >= candidateLocation) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentBishopLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation += 7;
            }

            // up and to the left
            candidateLocation = currentBishopLocation - 9;
            endPos = leftDiag.nextSetBit(0);
            while (candidateLocation >= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentBishopLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation -= 9;
            }

            // down and to the right
            candidateLocation = currentBishopLocation + 9;
            endPos = leftDiag.length() - 1;
            while (candidateLocation <= endPos) {
                if (alliedUnits.get(candidateLocation)) {
                    break;
                }
                setBits(candidateLocation);
                legalMoveImplementations.add(new MoveImplementation(currentBishopLocation,
                        candidateLocation, this));
                if (enemyUnits.get(candidateLocation)) {
                    break;
                }
                candidateLocation += 9;
            }

        }

        return legalMoveImplementations;
    }

    public List<MoveImplementation> calculateKnightLegals(final ChessBitSet knightBitSet) {

        final ChessBitSet alliedUnits = alliedPieces();
        final ChessBitSet enemyUnits = enemyPieces();
        final List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentKnightLocation = knightBitSet.nextSetBit(0); currentKnightLocation >= 0; currentKnightLocation = knightBitSet
                .nextSetBit(currentKnightLocation + 1)) {
            if (!(BitBoard.FILE_G.get(currentKnightLocation) || BitBoard.FILE_H
                    .get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation - 6;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_A.get(currentKnightLocation) || BitBoard.FILE_B
                    .get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation - 10;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_H.get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation - 15;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_A.get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation - 17;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_A.get(currentKnightLocation) || BitBoard.FILE_B
                    .get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation + 6;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_G.get(currentKnightLocation) || BitBoard.FILE_H
                    .get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation + 10;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_A.get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation + 15;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
            if (!(BitBoard.FILE_H.get(currentKnightLocation))) {
                final int candidateLocation = currentKnightLocation + 17;
                if (isTileValid(candidateLocation)) {
                    if (!alliedUnits.get(candidateLocation)
                            || enemyUnits.get(candidateLocation)) {
                        setBits(candidateLocation);
                        legalMoveImplementations.add(new MoveImplementation(currentKnightLocation,
                                candidateLocation, this));
                    }
                }
            }
        }

        return legalMoveImplementations;

    }

    public static ChessBitSet allPawns() {
        final ChessBitSet allPawns = new ChessBitSet();
        allPawns.or(BitBoard.whitePawns);
        allPawns.or(BitBoard.blackPawns);
        return allPawns;
    }

    public static ChessBitSet allKnights() {
        final ChessBitSet allKnights = new ChessBitSet();
        allKnights.or(BitBoard.whiteKnights);
        allKnights.or(BitBoard.blackKnights);
        return allKnights;
    }

    public static ChessBitSet allBishops() {
        final ChessBitSet allBishops = new ChessBitSet();
        allBishops.or(BitBoard.whiteBishops);
        allBishops.or(BitBoard.blackBishops);
        return allBishops;
    }

    public static ChessBitSet allRooks() {
        final ChessBitSet allRooks = new ChessBitSet();
        allRooks.or(BitBoard.whiteRooks);
        allRooks.or(BitBoard.blackRooks);
        return allRooks;
    }

    public static ChessBitSet allQueens() {
        final ChessBitSet allQueens = new ChessBitSet();
        allQueens.or(BitBoard.whiteQueens);
        allQueens.or(BitBoard.blackQueens);
        return allQueens;
    }

    public static ChessBitSet allKings() {
        final ChessBitSet allKings = new ChessBitSet();
        allKings.or(BitBoard.whiteKing);
        allKings.or(BitBoard.blackKing);
        return allKings;
    }

    public static ChessBitSet allPieces() {
        final ChessBitSet allPieces = new ChessBitSet();
        allPieces.or(BitBoard.whiteRooks);
        allPieces.or(BitBoard.whiteKnights);
        allPieces.or(BitBoard.whiteBishops);
        allPieces.or(BitBoard.whiteQueens);
        allPieces.or(BitBoard.whitePawns);
        allPieces.or(BitBoard.blackRooks);
        allPieces.or(BitBoard.blackKnights);
        allPieces.or(BitBoard.blackBishops);
        allPieces.or(BitBoard.blackQueens);
        allPieces.or(BitBoard.blackPawns);
        return allPieces;
    }

    public static ChessBitSet allWhitePieces() {
        final ChessBitSet allWhitePieces = new ChessBitSet();
        allWhitePieces.or(BitBoard.whiteRooks);
        allWhitePieces.or(BitBoard.whiteKnights);
        allWhitePieces.or(BitBoard.whiteBishops);
        allWhitePieces.or(BitBoard.whiteQueens);
        allWhitePieces.or(BitBoard.whitePawns);
        return allWhitePieces;
    }

    public static ChessBitSet allBlackPieces() {
        final ChessBitSet allBlackPieces = new ChessBitSet();
        allBlackPieces.or(BitBoard.blackRooks);
        allBlackPieces.or(BitBoard.blackKnights);
        allBlackPieces.or(BitBoard.blackBishops);
        allBlackPieces.or(BitBoard.blackQueens);
        allBlackPieces.or(BitBoard.blackPawns);
        return allBlackPieces;
    }

}