package com.chess.spring.utils.pgn;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.player.AbstractPlayer;
import com.chess.spring.engine.moves.simple.AbstractMove;

public interface PGNPersistence {

    void persistGame(Game game);

    AbstractMove getNextBestMove(Board board, AbstractPlayer player, String gameText);

}
