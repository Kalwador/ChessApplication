package com.chess.spring.engine.core.analysers;

import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.pieces.AbstractPiece;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Service
public class PawnAnalyserService {

    private static int ISOLATED_PAWN_PENALTY = -25;
    private static int DOUBLED_PAWN_PENALTY = -25;

    public int isolatedPawnPenalty(AbstractPlayer player) {
        return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int doubledPawnPenalty(AbstractPlayer player) {
        return calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int pawnStructureScore(AbstractPlayer player) {
        int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
        return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<AbstractPiece> calculatePlayerPawns(AbstractPlayer player) {
        List<AbstractPiece> playerPawnLocations = new ArrayList<>(8);
        for (AbstractPiece piece : player.getActivePieces()) {
            if (piece.getType().isPawn()) {
                playerPawnLocations.add(piece);
            }
        }
        return playerPawnLocations;
    }

    private static int calculatePawnColumnStack(int[] pawnsOnColumnTable) {
        int pawnStackPenalty = 0;
        for (int pawnStack : pawnsOnColumnTable) {
            if (pawnStack > 1) {
                pawnStackPenalty += pawnStack;
            }
        }
        return pawnStackPenalty * DOUBLED_PAWN_PENALTY;
    }

    private static int calculateIsolatedPawnPenalty(int[] pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if (pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[0];
        }
        if (pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[7];
        }
        for (int i = 1; i < pawnsOnColumnTable.length - 1; i++) {
            if ((pawnsOnColumnTable[i - 1] == 0 && pawnsOnColumnTable[i + 1] == 0)) {
                numIsolatedPawns += pawnsOnColumnTable[i];
            }
        }
        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    private static int[] createPawnColumnTable(Collection<AbstractPiece> playerPawns) {
        int[] table = new int[8];
        for (AbstractPiece playerPawn : playerPawns) {
            table[playerPawn.getPosition() % 8]++;
        }
        return table;
    }

}
