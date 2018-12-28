package com.chess.spring.engine.classic.player.ai;

import com.chess.spring.engine.classic.player.AbstractPlayer;
import com.chess.spring.engine.pieces.AbstractPiece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public  class PawnStructureAnalyzer {

    private static  PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();

    public static  int ISOLATED_PAWN_PENALTY = -25;
    public static  int DOUBLED_PAWN_PENALTY = -25;

    private PawnStructureAnalyzer() {
    }

    public static PawnStructureAnalyzer get() {
        return INSTANCE;
    }

    public int isolatedPawnPenalty( AbstractPlayer player) {
        return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int doubledPawnPenalty( AbstractPlayer player) {
        return calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int pawnStructureScore( AbstractPlayer player) {
         int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
        return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<AbstractPiece> calculatePlayerPawns(AbstractPlayer player) {
         List<AbstractPiece> playerPawnLocations = new ArrayList<>(8);
        for( AbstractPiece piece : player.getActivePieces()) {
            if(piece.getPieceType().isPawn()) {
                playerPawnLocations.add(piece);
            }
        }
        return ImmutableList.copyOf(playerPawnLocations);
    }

    private static int calculatePawnColumnStack( int[] pawnsOnColumnTable) {
        int pawnStackPenalty = 0;
        for( int pawnStack : pawnsOnColumnTable) {
            if(pawnStack > 1) {
                pawnStackPenalty += pawnStack;
            }
        }
        return pawnStackPenalty * DOUBLED_PAWN_PENALTY;
    }

    private static int calculateIsolatedPawnPenalty( int[] pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[0];
        }
        if(pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[7];
        }
        for(int i = 1; i < pawnsOnColumnTable.length - 1; i++) {
            if((pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0)) {
                numIsolatedPawns += pawnsOnColumnTable[i];
            }
        }
        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    private static int[] createPawnColumnTable( Collection<AbstractPiece> playerPawns) {
         int[] table = new int[8];
        for( AbstractPiece playerPawn : playerPawns) {
            table[playerPawn.getPiecePosition() % 8]++;
        }
        return table;
    }

}
