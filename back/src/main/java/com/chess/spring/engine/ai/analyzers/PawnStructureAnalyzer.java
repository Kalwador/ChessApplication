package com.chess.spring.engine.ai.analyzers;

import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.player.Player;
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

    public int pawnStructureScore( Player player) {
         int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
        return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<Piece> calculatePlayerPawns( Player player) {
         List<Piece> playerPawnLocations = new ArrayList<>(8);
        for( Piece piece : player.getActivePieces()) {
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

    private static int[] createPawnColumnTable( Collection<Piece> playerPawns) {
         int[] table = new int[8];
        for( Piece playerPawn : playerPawns) {
            table[playerPawn.getPiecePosition() % 8]++;
        }
        return table;
    }

}
