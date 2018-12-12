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
        public void setBits( int moveLocation) {
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
        public void setBits( int moveLocation) {
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
        public void setBits( int moveLocation) {
            BitBoard.whiteLegalLocations.set(moveLocation);
        }
    },
    WHITE_PAWNS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {

             List<MoveImplementation> legalMoveImplementations = new ArrayList<>();
             ChessBitSet allPieces = allPieces();
             ChessBitSet enemyPieces = enemyPieces();
             ChessBitSet pawnAdvances = new ChessBitSet(BitBoard.whitePawns);
            pawnAdvances.shift(-8);
            pawnAdvances.andNot(allPieces);
             ChessBitSet pawnJumps = new ChessBitSet(BitBoard.whitePawns);
            pawnJumps.shift(-16);
            pawnJumps.andNot(allPieces);
             ChessBitSet pawnAttacksLeft = new ChessBitSet(BitBoard.whitePawns);
            pawnAttacksLeft.shift(-9);
            pawnAttacksLeft.and(enemyPieces);
             ChessBitSet pawnAttacksRight = new ChessBitSet(BitBoard.whitePawns);
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
        public void setBits( int moveLocation) {
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
        public void setBits( int moveLocation) {
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
        public void setBits( int moveLocation) {
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
        public void setBits( int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }
    },
    BLACK_PAWNS {
        @Override
        public List<MoveImplementation> calculateLegalMoves() {

             List<MoveImplementation> legalMoveImplementations = new ArrayList<>();
             ChessBitSet allPieces = allPieces();
             ChessBitSet enemyPieces = enemyPieces();
             ChessBitSet pawnAdvances = new ChessBitSet(BitBoard.blackPawns);
            pawnAdvances.shift(8);
            pawnAdvances.andNot(allPieces);
             ChessBitSet pawnJumps = new ChessBitSet(BitBoard.blackPawns);
            pawnJumps.shift(16);
            pawnJumps.andNot(allPieces);
             ChessBitSet pawnAttacksLeft = new ChessBitSet(BitBoard.blackPawns);
            pawnAttacksLeft.shift(9);
            pawnAttacksLeft.and(enemyPieces);
             ChessBitSet pawnAttacksRight = new ChessBitSet(BitBoard.blackPawns);
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
        public void setBits( int moveLocation) {
            BitBoard.blackLegalLocations.set(moveLocation);
        }
    };

    public abstract List<MoveImplementation> calculateLegalMoves();

    public abstract ChessBitSet alliedPieces();

    public abstract ChessBitSet enemyPieces();

    public abstract void setBits(int moveLocation);

    public boolean isOccupied( int position) {
        return allPieces().get(position);
    }

    public List<MoveImplementation> calculateKingLegals( ChessBitSet kingBitSet) {

         ChessBitSet alliedUnits = alliedPieces();
         int kingPos = kingBitSet.nextSetBit(0);
        int moveLocation = kingPos - 1;
         List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

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

    private static boolean isTileValid( int moveLocation) {
        return moveLocation >= 0 && moveLocation < 64;
    }


    public List<MoveImplementation> calculateRookLegals( ChessBitSet rookBitSet) {

         ChessBitSet alliedUnits = alliedPieces();
         ChessBitSet enemyUnits = enemyPieces();
         List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentRookLocation = rookBitSet.nextSetBit(0); currentRookLocation >= 0; currentRookLocation = rookBitSet
                .nextSetBit(currentRookLocation + 1)) {

             ChessBitSet vertical = BitBoard.ALL_FILES.get(currentRookLocation);
             ChessBitSet horizontal = BitBoard.ALL_RANKS
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

    public List<MoveImplementation> calculateBishopLegals( ChessBitSet bishopBitSet) {

         ChessBitSet alliedUnits = alliedPieces();
         ChessBitSet enemyUnits = enemyPieces();
         List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentBishopLocation = bishopBitSet.nextSetBit(0); currentBishopLocation >= 0; currentBishopLocation = bishopBitSet
                .nextSetBit(currentBishopLocation + 1)) {

             ChessBitSet rightDiag = BitBoard.ALL_RIGHT_DIAGONALS
                    .get(currentBishopLocation);
             ChessBitSet leftDiag = BitBoard.ALL_LEFT_DIAGONALS
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

    public List<MoveImplementation> calculateKnightLegals( ChessBitSet knightBitSet) {

         ChessBitSet alliedUnits = alliedPieces();
         ChessBitSet enemyUnits = enemyPieces();
         List<MoveImplementation> legalMoveImplementations = new ArrayList<>();

        for (int currentKnightLocation = knightBitSet.nextSetBit(0); currentKnightLocation >= 0; currentKnightLocation = knightBitSet
                .nextSetBit(currentKnightLocation + 1)) {
            if (!(BitBoard.FILE_G.get(currentKnightLocation) || BitBoard.FILE_H
                    .get(currentKnightLocation))) {
                 int candidateLocation = currentKnightLocation - 6;
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
                 int candidateLocation = currentKnightLocation - 10;
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
                 int candidateLocation = currentKnightLocation - 15;
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
                 int candidateLocation = currentKnightLocation - 17;
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
                 int candidateLocation = currentKnightLocation + 6;
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
                 int candidateLocation = currentKnightLocation + 10;
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
                 int candidateLocation = currentKnightLocation + 15;
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
                 int candidateLocation = currentKnightLocation + 17;
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
         ChessBitSet allPawns = new ChessBitSet();
        allPawns.or(BitBoard.whitePawns);
        allPawns.or(BitBoard.blackPawns);
        return allPawns;
    }

    public static ChessBitSet allKnights() {
         ChessBitSet allKnights = new ChessBitSet();
        allKnights.or(BitBoard.whiteKnights);
        allKnights.or(BitBoard.blackKnights);
        return allKnights;
    }

    public static ChessBitSet allBishops() {
         ChessBitSet allBishops = new ChessBitSet();
        allBishops.or(BitBoard.whiteBishops);
        allBishops.or(BitBoard.blackBishops);
        return allBishops;
    }

    public static ChessBitSet allRooks() {
         ChessBitSet allRooks = new ChessBitSet();
        allRooks.or(BitBoard.whiteRooks);
        allRooks.or(BitBoard.blackRooks);
        return allRooks;
    }

    public static ChessBitSet allQueens() {
         ChessBitSet allQueens = new ChessBitSet();
        allQueens.or(BitBoard.whiteQueens);
        allQueens.or(BitBoard.blackQueens);
        return allQueens;
    }

    public static ChessBitSet allKings() {
         ChessBitSet allKings = new ChessBitSet();
        allKings.or(BitBoard.whiteKing);
        allKings.or(BitBoard.blackKing);
        return allKings;
    }

    public static ChessBitSet allPieces() {
         ChessBitSet allPieces = new ChessBitSet();
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
         ChessBitSet allWhitePieces = new ChessBitSet();
        allWhitePieces.or(BitBoard.whiteRooks);
        allWhitePieces.or(BitBoard.whiteKnights);
        allWhitePieces.or(BitBoard.whiteBishops);
        allWhitePieces.or(BitBoard.whiteQueens);
        allWhitePieces.or(BitBoard.whitePawns);
        return allWhitePieces;
    }

    public static ChessBitSet allBlackPieces() {
         ChessBitSet allBlackPieces = new ChessBitSet();
        allBlackPieces.or(BitBoard.blackRooks);
        allBlackPieces.or(BitBoard.blackKnights);
        allBlackPieces.or(BitBoard.blackBishops);
        allBlackPieces.or(BitBoard.blackQueens);
        allBlackPieces.or(BitBoard.blackPawns);
        return allBlackPieces;
    }

}