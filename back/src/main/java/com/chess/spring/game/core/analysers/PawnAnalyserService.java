package com.chess.spring.game.core.analysers;

import com.chess.spring.game.pieces.AbstractPiece;
import com.chess.spring.game.player.AbstractPlayer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class PawnAnalyserService {

    private static int ISOLATED_PENALTY = -25;
    private static int DOUBLED_PENALTY = -25;

    public int pawnStructureScore(AbstractPlayer player) {
        int[] pawns = setPawnsInColumn(getPlayerPawns(player));
        return bonusForPawnStructure(pawns) + penaltyForIsolatedPawns(pawns);
    }

    private static List<AbstractPiece> getPlayerPawns(AbstractPlayer player) {
        return player.getActivePieces().stream()
                .filter(piece -> piece.getType().isPawn())
                .collect(Collectors.toList());
    }

    private static int[] setPawnsInColumn(List<AbstractPiece> pawns) {
        int[] table = new int[BoardConfiguration.TILES_PER_ROW];
        for (AbstractPiece pawn : pawns) {
            table[pawn.getPosition() % BoardConfiguration.TILES_PER_ROW]++;
        }
        return table;
    }

    private static int bonusForPawnStructure(int[] pawnColumn) {
        int pawnsInStructure = 0;
        for (int pawn : pawnColumn) {
            if (pawn > 1) {
                pawnsInStructure += pawn;
            }
        }
        return pawnsInStructure * DOUBLED_PENALTY;
    }

    private static int penaltyForIsolatedPawns(int[] pawnColumn) {
        int isolated = 0;
        if (pawnColumn[0] > 0 && pawnColumn[1] == 0) {
            isolated += pawnColumn[0];
        }
        if (pawnColumn[7] > 0 && pawnColumn[6] == 0) {
            isolated += pawnColumn[7];
        }
        for (int i = 1; i < pawnColumn.length - 1; i++) {
            if ((pawnColumn[i - 1] == 0 && pawnColumn[i + 1] == 0)) {
                isolated += pawnColumn[i];
            }
        }
        return isolated * ISOLATED_PENALTY;
    }


}
