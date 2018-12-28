package com.chess.spring.services.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvEDTO;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.move.simple.Move;
import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.entities.account.Account;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.game.PlayerColor;
import com.chess.spring.models.game.GameEndStatus;
import com.chess.spring.models.game.GamePvEStatus;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.repositories.GamePvERepository;
import com.chess.spring.services.account.AccountService;
import com.chess.spring.utils.pgn.FenUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GamePvEServiceImpl extends GameUtils implements GamePvEService {
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

    @Override
    public Page<GamePvEDTO> getAll(Pageable page) {
        List<GamePvEStatus> statuses = Arrays.asList(GamePvEStatus.BLACK_WIN, GamePvEStatus.WHITE_WIN, GamePvEStatus.DRAW);
        return GamePvEDTO.map(this.gamePvERepository.findByStatusNotIn(page, statuses), page);
    }

    public GamePvE getById(Long gameId) throws ResourceNotFoundException {
        return gamePvERepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.GAME_NOT_FOUND.getInfo()));
    }

    private String getBoardById(Long gameId) throws ResourceNotFoundException {
        return gamePvERepository.getBoardByGameId(gameId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.GAME_NOT_FOUND.getInfo()));
    }

    @Override
    public Long startNewGame(GamePvEDTO gamePvEDTO) throws ResourceNotFoundException, DataMissmatchException {
        Account account = accountService.getCurrent();
        if (gamePvEDTO.getLevel() < 1 || gamePvEDTO.getLevel() > 5) {
            throw new DataMissmatchException(ExceptionMessages.GAME_LEVEL_NOT_VALID.getInfo());
        }

        GamePvE game = buildGame(gamePvEDTO, account);
        game = gamePvERepository.save(game);
        account.getPveGames().add(game);
        accountRepository.save(account);
        return game.getId();
    }

    private GamePvE buildGame(GamePvEDTO gamePvEDTO, Account account) {
        Board board = Board.createStandardBoard();
        PlayerColor color = gamePvEDTO.getColor();
        if (gamePvEDTO.getColor().equals(PlayerColor.RANDOM)) {
            color = drawColor();
        }

        if (color == PlayerColor.BLACK) {
            Move move = getBestMove(board, gamePvEDTO.getLevel());
            try {
                board = executeMove(board, move);
            } catch (InvalidDataException e) {
                //nie mozliwe, bo pierwszy ruch musi być poprawny
                throw new RuntimeException("Error during first computer move, quite imposible ... but here we are");
            }
        }

        return GamePvE.builder()
                .account(account)
                .color(color)
                .level(gamePvEDTO.getLevel())
//                .timePerMove() //TODO-TIMING
                .board(FenUtilities.createFENFromGame(board))
                .moves("")
                .status(GamePvEStatus.PLAYER_MOVE)
                .gameStarted(LocalDate.now())
                .build();
    }

    @Override
    public MoveDTO makeMove(Long gameId, MoveDTO moveDTOPvE) throws InvalidDataException, DataMissmatchException, LockedSourceException, ResourceNotFoundException {
        GamePvE game = getById(gameId);
        checkStatus(game.getStatus(), GamePvEStatus.PLAYER_MOVE);

        Board boardAfterPlayerMove = executeMove(game.getBoard(), moveDTOPvE);

        GameEndStatus gameEndStatus = checkEndOfGame(boardAfterPlayerMove);
        if (gameEndStatus != null) {
            return handleEndOfGame(game, boardAfterPlayerMove, gameEndStatus, true);
        } else {
            //Game still in progress
            Move move = getBestMove(boardAfterPlayerMove, game.getLevel());
            Board boardAfterComputerResponse = executeMove(boardAfterPlayerMove, move);

            gameEndStatus = checkEndOfGame(boardAfterComputerResponse);
            game.setBoard(FenUtilities.createFENFromGame(boardAfterComputerResponse));

            if (gameEndStatus != null) {
                return handleEndOfGame(game, boardAfterComputerResponse, gameEndStatus, false);
            } else {
                gamePvERepository.save(game);

                return MoveDTO.builder()
                        .source(move.getCurrentCoordinate())
                        .destination(move.getDestination())
                        .isInCheck(boardAfterComputerResponse.currentPlayer().isInCheck())
                        .type(move.getClass().getSimpleName())
                        .statusPve(GamePvEStatus.PLAYER_MOVE)
                        .build();
            }
        }
    }

    private void checkStatus(GamePvEStatus gamePvEStatus, GamePvEStatus status) throws DataMissmatchException, LockedSourceException {
        switch (gamePvEStatus) {
            case DRAW:
            case WHITE_WIN:
            case BLACK_WIN:
                throw new LockedSourceException(ExceptionMessages.GAME_END.getInfo());
        }
        if (!gamePvEStatus.equals(status))
            throw new DataMissmatchException(ExceptionMessages.GAME_STATUS_NOT_VALID.getInfo());
    }


    @Override
    public void stopGame(Long gameId) {
//        GamePvE gamePvE = gamePvE.setStatus(GamePvEStatus.ON_HOLD);
        //TODO zatrzymac timer
//        gamePvERepository.save(gamePvE);
    }

    private MoveDTO handleEndOfGame(GamePvE game, Board board, GameEndStatus gameEndStatus, boolean isPlayerMove) {
        game.setBoard(FenUtilities.createFENFromGame(board));
        GamePvEStatus status;
        if (gameEndStatus == GameEndStatus.STALE_MATE) {
            status = GamePvEStatus.DRAW;
        } else {
            if (isPlayerMove) {
                status = game.getColor() == PlayerColor.WHITE ? GamePvEStatus.WHITE_WIN : GamePvEStatus.BLACK_WIN;
            } else {
                status = game.getColor() == PlayerColor.WHITE ? GamePvEStatus.BLACK_WIN : GamePvEStatus.WHITE_WIN;
            }
        }
        game.setStatus(status);
        gamePvERepository.save(game);
        return new MoveDTO(null, null, null, false, status, null, null, null);
    }

    @Override
    public List<MoveDTO> getLegateMoves(Long gameId) throws ResourceNotFoundException {
        Board board = FenUtilities.createGameFromFEN(getBoardById(gameId));
        return MoveDTO.map(board.getAllLegalMoves());
    }

    @Override
    public void forfeit(Long gameId) throws ResourceNotFoundException {
        GamePvE game = getById(gameId);
        game.setStatus(game.getColor() == PlayerColor.WHITE ? GamePvEStatus.BLACK_WIN : GamePvEStatus.WHITE_WIN);
        gamePvERepository.save(game);
    }
}
