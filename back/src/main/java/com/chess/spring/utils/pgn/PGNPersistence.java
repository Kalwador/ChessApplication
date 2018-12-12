package com.chess.spring.utils.pgn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.Move;
import com.chess.spring.engine.classic.player.Player;

public interface PGNPersistence {

    void persistGame(Game game);

    Move getNextBestMove(Board board, Player player, String gameText);

}
