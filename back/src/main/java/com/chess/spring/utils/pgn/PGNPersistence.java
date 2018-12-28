package com.chess.spring.utils.pgn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.classic.player.AbstractPlayer;
import com.chess.spring.engine.move.simple.Move;

public interface PGNPersistence {

    void persistGame(Game game);

    Move getNextBestMove(Board board, AbstractPlayer player, String gameText);

}
