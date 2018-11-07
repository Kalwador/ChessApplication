//package com.chess.spring.services.game;
//
//import com.chess.spring.dto.MoveDTOPvE;
//import com.chess.spring.dto.game.GamePvPDTO;
//import com.chess.spring.dto.game.SocketResponseDTO;
//import com.chess.spring.engine.classic.board.Board;
//import com.chess.spring.engine.classic.board.Move;
//import com.chess.spring.entities.account.Account;
//import com.chess.spring.entities.game.GamePvP;
//import com.chess.spring.exceptions.*;
//import com.chess.spring.models.game.*;
//import com.chess.spring.repositories.AccountRepository;
//import com.chess.spring.repositories.GamePvPRepository;
//import com.chess.spring.services.account.AccountService;
//import com.chess.spring.utils.pgn.FenUtilities;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class GamePvPServiceImpl extends GameUtils {
//    private GamePvPRepository gamePvPRepository;
//    private AccountService accountService;
//    private AccountRepository accountRepository;
//
//    @Autowired
//    public GamePvPServiceImpl(GamePvPRepository gamePvPRepository,
//                              AccountService accountService,
//                              AccountRepository accountRepository) {
//        this.gamePvPRepository = gamePvPRepository;
//        this.accountService = accountService;
//        this.accountRepository = accountRepository;
//    }
//
////TODO-PAGINATION
//public List<GamePvE> getAll() {
//        return this.gamePvERepository.findAll();
//        }

//    public GamePvP getById(Long gameId) throws InvalidDataException {
//        return gamePvPRepository.findById(gameId).orElseThrow(() -> new InvalidDataException("Gra nie odnaleziona"));
//    }
//
//    public GamePvP findGame(GamePvPDTO gamePvPDTO) throws ResourceNotFoundException {
//        Account account = accountService.getDetails();
//        GamePvP game = gamePvPRepository
//                .findFirstByStatus(GamePvPStatus.ROOM)
//                .orElseGet(() -> startNewGame(gamePvPDTO, account));
//        //TODO - more to do
//        return game;
//    }
//
//    private GamePvP startNewGame(GamePvPDTO gamePvPDTO, Account account) {
//        GamePvP game = buildGame(gamePvPDTO, account);
//        game = gamePvPRepository.save(game);
//        account.getPvpGames().add(game);
//        accountRepository.save(account);
//        return game;
//    }
//
//    private GamePvP buildGame(GamePvPDTO gamePvPDTO, Account account) {
//        PlayerColor color = drawColor();
//        return GamePvP.builder()
//                .whitePlayer(color == PlayerColor.WHITE ? account : null)
//                .blackPlayer(color == PlayerColor.BLACK ? account : null)
////                .timePerMove() //TODO-TIMING
//                .board(FenUtilities.createFENFromGame(Board.createStandardBoard()))
//                .moves("")
//                .status(GamePvPStatus.WHITE_MOVE)
//                .gameStarted(LocalDate.now())
//                .build();
//    }
//
//    public SocketResponseDTO handleResponse(Long gameId, SocketResponseDTO response) throws DataMissmatchException, LockedSourceException, InvalidDataException, NotExpectedError {
//        switch (response.getStatus()) {
//            case WHITE_MOVE:
//            case BLACK_MOVE: {
//                response = executeMove(gameId, response);
//            }
//        }
//        return response;
//    }
//
//    public SocketResponseDTO executeMove(Long gameId, SocketResponseDTO response) throws
//            InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError {
//        GamePvP game = getById(gameId);
//        checkGameStatus(game.getStatus());
//
//        Board boardAfterPlayerMove = executeMove(game.getBoard(), response.getMoveDTOPvE());
//
//        GameEndStatus gameEndStatus = checkEndOfGame(boardAfterPlayerMove);
//        if (gameEndStatus != null) {
//            return handleEndOfGame(response, game, gameEndStatus);
//        } else {
//
//
//            //Game still in progress
//            Move move = getBestMove(boardAfterPlayerMove, game.getLevel());
//            Board boardAfterComputerResponse = executeMove(boardAfterPlayerMove, move);
//
//            gameEndStatus = checkEndOfGame(boardAfterComputerResponse);
//            if (gameEndStatus != null) {
//                handleEndOfGame(game, boardAfterComputerResponse, gameEndStatus, false);
//            } else {
//                game.setBoard(FenUtilities.createFENFromGame(boardAfterComputerResponse));
//                gamePvERepository.save(game);
//                return MoveDTOPvE.map(move);
//            }
//        }
//        throw new NotExpectedError("nie powinienes sie tu znalezc");
//    }
//
//    private void checkGameStatus(GamePvPStatus status) throws
//            DataMissmatchException, LockedSourceException {
//        switch (status) {
//            case CHAT:
//            case WHITE_MOVE:
//            case BLACK_MOVE: {
//                break;
//            }
//            case DRAW:
//            case WHITE_WIN:
//            case BLACK_WIN: {
//                throw new LockedSourceException("Gra się zakończyła");
//            }
//            default: {
//                throw new DataMissmatchException("Nie poprawny status nowej gry");
//            }
//        }
//    }
//
//    private GameEndStatus checkEndOfGame(Board board) {
//        if (board.currentPlayer().isInCheckMate()) {
//            return GameEndStatus.CHECKMATE;
//        }
//        if (board.currentPlayer().isInStaleMate()) {
//            return GameEndStatus.STALE_MATE;
//        }
//        return null;
//    }
//
//    private MoveDTOPvE handleEndOfGame(GamePvP game, Board board, GameEndStatus gameEndStatus, PlayerColor playerMoveColor) {
//        GamePvPStatus status = null;
//        if (gameEndStatus == GameEndStatus.STALE_MATE) {
//            status = GamePvPStatus.DRAW;
//        } else {
//            switch (playerMoveColor){
//                case WHITE: {
//                    status = GamePvPStatus.WHITE_WIN;
//                    break;
//                }
//                case BLACK: {
//                    status = GamePvPStatus.BLACK_WIN;
//                    break;
//                }
//            }
//        }
//        game.setStatus(status);
//        game.setBoard(FenUtilities.createFENFromGame(board));
//        gamePvPRepository.save(game);
//        return new MoveDTOPvE(null, null, null, status, "");
//    }
//
//    public GameWinner getWinner(Long gameId) throws InvalidDataException {
//        return Optional.ofNullable(getById(gameId).getGameWinner()).orElseThrow(InvalidDataException::new);
//    }
//
//    public List<MoveDTOPvE> getLegateMoves(Long gameId) throws InvalidDataException {
//        GamePvP game = getById(gameId);
//        Board board = FenUtilities.createGameFromFEN(game.getBoard());
//        return MoveDTOPvE.map(board.getAllLegalMoves());
//    }
//}
