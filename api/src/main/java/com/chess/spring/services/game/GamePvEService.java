package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.engine.classic.board.MoveTransition;
import com.chess.spring.entities.GamePvE;
import com.chess.spring.entities.account.Account;
import com.chess.spring.exceptions.game.GameNotExistException;
import com.chess.spring.exceptions.game.GameRuntimeException;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GamePvEStatus;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.repositories.GamePvERepository;
import com.chess.spring.services.account.AccountService;
import com.chess.spring.utils.pgn.FenUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class GamePvEService extends GameService {
    private GamePvERepository gamePvERepository;
    private AccountService accountService;
    private AccountRepository accountRepository;

    @Autowired
    public GamePvEService(GamePvERepository gamePvERepository,
                          AccountService accountService,
                          AccountRepository accountRepository) {
        this.gamePvERepository = gamePvERepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    private GamePvE getById(Long id) {
        return gamePvERepository.findById(gameId).orElseThrow(GameNotExistException::new);
    }

    public GamePvEDTO startNewGame(GamePvEDTO gamePvEDTO) {
        Account account = accountService.getDetails();

        PlayerColor color = gamePvEDTO.getColor();
        if (gamePvEDTO.getColor().equals(PlayerColor.RANDOM)) {
            color = drawColor();
        }
        if (gamePvEDTO.getLevel() < 1 || gamePvEDTO.getLevel() > 5) {
            throw new GameRuntimeException();
        }

        GamePvE game = GamePvE.builder()
                .account(account)
                .color(color)
                .level(gamePvEDTO.getLevel())
//                .timePerMove() //TODO-TIMING
                .board(FenUtilities.createFENFromGame(Board.createStandardBoard()))
                .moves("")
                .status(GamePvEStatus.READY)
                .gameStarted(LocalDate.now())
                .build();

        game = gamePvERepository.save(game);
        account.getPveGames().add(game);
        accountRepository.save(account);
        return GamePvEDTO.convert(game);
    }

    public MoveDTO makeFirstMove(Long gameId) {
        final AIThinkTank thinkTank = new AIThinkTank();
        thinkTank.execute();
    }

    public MoveDTO makeMove(Long gameId, MoveDTO moveDTO) {
        GamePvE game = gamePvERepository.findById(gameId).orElseThrow(GameNotExistException::new);
        if (game.getStatus() != GamePvEStatus.READY) {
            throw new GameRuntimeException();
        }
        game.setStatus(GamePvEStatus.PLAYER_MOVE);
        Board board = FenUtilities.createGameFromFEN(game.getBoard());
        final Move move = Move.MoveFactory.createMove(board, moveDTO.getSource(), moveDTO.getDestination());
        final MoveTransition transition = board.currentPlayer().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            board = transition.getToBoard();
//            moveLog.addMove(move);
        }

        game.setBoard(FenUtilities.createFENFromGame(board));
        //TODO needs more work
    }

    public void stopGame(Long gameId) {
        GamePvE gamePvE =
                gamePvE.setStatus(GamePvEStatus.WAIT);
        //TODO zatrzymac timer
        gamePvERepository.save(gamePvE);
    }
}
