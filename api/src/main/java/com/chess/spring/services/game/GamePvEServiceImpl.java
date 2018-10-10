package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.engine.classic.board.Move;
import com.chess.spring.engine.classic.player.ai.StockAlphaBeta;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.entities.account.Account;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.status.GameEndStatus;
import com.chess.spring.models.status.GameWinner;
import com.chess.spring.models.status.GamePvEStatus;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.repositories.GamePvERepository;
import com.chess.spring.services.account.AccountService;
import com.chess.spring.utils.pgn.FenUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class GamePvEServiceImpl extends GameUtils {
    private GamePvERepository gamePvERepository;
    private AccountService accountService;
    private AccountRepository accountRepository;

    @Autowired
    public GamePvEServiceImpl(GamePvERepository gamePvERepository,
                              AccountService accountService,
                              AccountRepository accountRepository) {
        this.gamePvERepository = gamePvERepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    private GamePvE getById(Long gameId) throws InvalidDataException {
        return gamePvERepository.findById(gameId).orElseThrow(() -> new InvalidDataException("Gra nie odnaleziona"));
    }

    /**
     * Create new game pve
     *
     * @param gamePvEDTO
     * @return
     */
    public GamePvEDTO startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        Account account = accountService.getDetails();
        if (gamePvEDTO.getLevel() < 1 || gamePvEDTO.getLevel() > 5) {
            throw new DataMissmatchException("Poziom poza skalą");
        }

        GamePvE game = buildGame(gamePvEDTO, account);

        game = gamePvERepository.save(game);
        account.getPveGames().add(game);
        accountRepository.save(account);
        return GamePvEDTO.convert(game);
    }

    private GamePvE buildGame(GamePvEDTO gamePvEDTO, Account account) {
        PlayerColor color = gamePvEDTO.getColor();
        if (gamePvEDTO.getColor().equals(PlayerColor.RANDOM)) {
            color = drawColor();
        }
        return GamePvE.builder()
                .account(account)
                .color(color)
                .level(gamePvEDTO.getLevel())
//                .timePerMove() //TODO-TIMING
                .board(FenUtilities.createFENFromGame(Board.createStandardBoard()))
                .moves("")
                .status(GamePvEStatus.READY)
                .gameStarted(LocalDate.now())
                .build();
    }

    public MoveDTO makeMove(Long gameId, MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError {
        GamePvE game = getById(gameId);
        checkStatus(game.getStatus(), GamePvEStatus.PLAYER_MOVE);

        Board boardAfterPlayerMove = executeMove(game.getBoard(), moveDTO);

        GameEndStatus gameEndStatus = checkEndOfGame(boardAfterPlayerMove);
        if (gameEndStatus != null) {
            handleEndOfGame(game, boardAfterPlayerMove, gameEndStatus, true);
        } else {
            //Game still in progress
            Move move = getBestMove(boardAfterPlayerMove, game.getLevel());
            Board boardAfterComputerResponse = executeMove(boardAfterPlayerMove, move);

            gameEndStatus = checkEndOfGame(boardAfterComputerResponse);
            if (gameEndStatus != null) {
                handleEndOfGame(game, boardAfterComputerResponse, gameEndStatus, false);
            } else {
                game.setBoard(FenUtilities.createFENFromGame(boardAfterComputerResponse));
                gamePvERepository.save(game);
                return MoveDTO.map(move);
            }
        }
        throw new NotExpectedError("nie powinienes sie tu znalezc");
    }

    public MoveDTO makeFirstMove(Long gameId) throws InvalidDataException, DataMissmatchException, LockedSourceException {
        GamePvE game = getById(gameId);
        checkStatus(game.getStatus(), GamePvEStatus.READY);
        Board board = FenUtilities.createGameFromFEN(game.getBoard());
        Move move = getBestMove(board, game.getLevel());

        game.setStatus(GamePvEStatus.PLAYER_MOVE);
        game.setBoard(FenUtilities.createFENFromGame(board));
        gamePvERepository.save(game);

        return MoveDTO.map(move);
    }


    private Move getBestMove(Board board, int level) {
        final StockAlphaBeta strategy = new StockAlphaBeta(level);
//        strategy.addObserver(Table.get().getDebugPanel());
        return strategy.execute(board);
    }

    private void checkStatus(GamePvEStatus gamePvEStatus, GamePvEStatus status) throws DataMissmatchException, LockedSourceException {
        switch (gamePvEStatus) {
            case OVER:
                throw new LockedSourceException("Gra się zakończyła");
        }
        if (!gamePvEStatus.equals(status)) throw new DataMissmatchException("Nie poprawny status nowej gry");
    }

    private GameEndStatus checkEndOfGame(Board board) {
        if (board.currentPlayer().isInCheckMate()) {
            return GameEndStatus.CHECKMATE;
        }
        if (board.currentPlayer().isInStaleMate()) {
            return GameEndStatus.STALE_MATE;
        }
        return null;
    }


    public void stopGame(Long gameId) {
//        GamePvE gamePvE = gamePvE.setStatus(GamePvEStatus.ON_HOLD);
        //TODO zatrzymac timer
//        gamePvERepository.save(gamePvE);
    }

    public void handleEndOfGame(GamePvE game, Board board, GameEndStatus gameEndStatus, boolean isPlayerMove) throws LockedSourceException {
        game.setBoard(FenUtilities.createFENFromGame(board));
        game.setStatus(GamePvEStatus.OVER);
        if (gameEndStatus == GameEndStatus.STALE_MATE) {
            game.setGameWinner(GameWinner.DRAW);
        } else {
            if (isPlayerMove) {
                game.setGameWinner(game.getColor() == PlayerColor.WHITE ? GameWinner.WHITE_WIN : GameWinner.BLACK_WIN);
            } else {
                game.setGameWinner(game.getColor() == PlayerColor.WHITE ? GameWinner.BLACK_WIN : GameWinner.WHITE_WIN);
            }
        }
        gamePvERepository.save(game);
        throw new LockedSourceException("Gra się zakończyła");
    }


    public GameWinner getWinner(Long gameId) throws InvalidDataException {
        return Optional.ofNullable(getById(gameId).getGameWinner()).orElseThrow(InvalidDataException::new);
    }
}
