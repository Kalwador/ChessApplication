package com.chess.spring.game.pvp;

import com.chess.spring.communication.chat.ChatDTO;
import com.chess.spring.communication.chat.ChatService;
import com.chess.spring.communication.sockets.SocketEmitter;
import com.chess.spring.communication.sockets.SocketMessageDTO;
import com.chess.spring.communication.sockets.SocketMessageType;
import com.chess.spring.exceptions.*;
import com.chess.spring.game.*;
import com.chess.spring.game.board.Board;
import com.chess.spring.game.core.algorithms.AlphaBetaAlgorithm;
import com.chess.spring.game.moves.MoveDTO;
import com.chess.spring.game.pieces.utils.PlayerColor;
import com.chess.spring.profile.account.Account;
import com.chess.spring.profile.account.AccountRepository;
import com.chess.spring.profile.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GamePvPServiceImpl extends GameService implements GamePvPService {
    private GamePvPRepository gamePvPRepository;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private ChatService chatService;
    private SocketEmitter socketEmitter;

    @Autowired
    public GamePvPServiceImpl(EntityManager entityManager,
                              AlphaBetaAlgorithm alphaBetaAlgorithm,
                              ApplicationEventPublisher applicationEventPublisher,
                              GamePvPRepository gamePvPRepository,
                              AccountService accountService,
                              AccountRepository accountRepository,
                              SocketEmitter socketEmitter,
                              GameService gameService,
                              ChatService chatService) {
        super(entityManager, alphaBetaAlgorithm, applicationEventPublisher);
        this.gamePvPRepository = gamePvPRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.socketEmitter = socketEmitter;
        this.chatService = chatService;
    }

    @Override
    public Page<GamePvPDTO> getAll(Pageable page) throws ResourceNotFoundException {
        List<GamePvPStatus> statuses = Arrays.asList(GamePvPStatus.BLACK_MOVE, GamePvPStatus.WHITE_MOVE);
        List<GamePvP> results = getQueryForGames(accountService.getCurrent(), statuses, page).getResultList();
        return GamePvPDTO.map(results, page);
    }

    @Override
    public GamePvP getById(Long gameId) throws ResourceNotFoundException {
        return gamePvPRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.GAME_NOT_FOUND.getInfo()));
    }

    @Override
    public String getBoardById(Long gameId) throws ResourceNotFoundException {
        return gamePvPRepository.getBoardByGameId(gameId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.GAME_NOT_FOUND.getInfo()));
    }

    @Override
    public Long newGame(GamePvPDTO gamePvPDTO) throws ResourceNotFoundException {
        return startNewGame(gamePvPDTO, accountService.getCurrent()).getId();
    }

    @Override
    public Long findGame(GamePvPDTO gamePvPDTO) throws ResourceNotFoundException {
        Account account = accountService.getCurrent();
        GamePvP game;
        Optional<GamePvP> optionalGamePvP = this.getEmptyRoom(account);
        if (optionalGamePvP.isPresent()) {
            game = optionalGamePvP.get();
            if (game.getWhitePlayer() == null) {
                game.setWhitePlayer(account);
            } else {
                game.setBlackPlayer(account);
            }
            game.setStatus(GamePvPStatus.WHITE_MOVE);
            game.setGameStarted(LocalDate.now());
            this.gamePvPRepository.save(game);
            emitGameJoinEvent(account, game);
        } else {
            game = startNewGame(gamePvPDTO, account);
        }
        return game.getId();
    }

    public void emitGameJoinEvent(Account account, GamePvP game) {
        SocketMessageDTO messageDTO = SocketMessageDTO.builder()
                .type(SocketMessageType.START_GAME)
                .sender(account.getNick())
                .chatMessage("Gracz " + account.getNick() + " dołączył do gry")
                .build();
        this.socketEmitter.distributeMessage(game.getId().toString(), messageDTO);
    }

    @Override
    public Long startGame(Account account, GamePvP game) {
        if (game.getWhitePlayer() == null) {
            game.setWhitePlayer(account);
        } else {
            game.setBlackPlayer(account);
        }
        game.setStatus(GamePvPStatus.WHITE_MOVE);
        game.setGameStarted(LocalDate.now());
        this.gamePvPRepository.save(game);

        emitGameJoinEvent(account, game);
        return game.getId();
    }

    @Override
    public void makeMove(Long gameId, MoveDTO moveDTO) throws InvalidDataException, DataMissmatchException, LockedSourceException, ResourceNotFoundException, PreconditionFailedException {
        GamePvP game = getById(gameId);
        Account account = this.accountService.getCurrent();

        checkIsPlayerInGame(game, account);

        SocketMessageDTO message = new SocketMessageDTO();
        message.setSender(account.getId().toString());
        message.setMoveDTO(new MoveDTO());

        validateGameStatus(game.getStatus());
        Board boardAfterPlayerMove = executeMove(game.getBoard(), moveDTO);

        GameEndStatus gameEndStatus = checkEndOfGame(boardAfterPlayerMove);
        if (gameEndStatus != null) {
            GamePvPStatus status = handleEndOfGame(game, moveDTO, gameEndStatus);
            game.setStatus(status);
            message.getMoveDTO().setStatusPvP(status);
            message.setType(SocketMessageType.END_GAME);
            message.setChatMessage(status.getInfo());
        } else {
            GamePvPStatus status = game.getStatus() == GamePvPStatus.WHITE_MOVE ? GamePvPStatus.BLACK_MOVE : GamePvPStatus.WHITE_MOVE;
            game.setStatus(status);
            message.setType(SocketMessageType.MOVE);
            message.getMoveDTO().setStatusPvP(status);
            message.getMoveDTO().setInCheck(boardAfterPlayerMove.getCurrentPlayer().isInCheck());
            message.getMoveDTO().setType(map(boardAfterPlayerMove, moveDTO).getClass().getSimpleName());
        }

        message.getMoveDTO().setSource(moveDTO.getSource());
        message.getMoveDTO().setDestination(moveDTO.getDestination());


        game.setBoard(FenService.parse(boardAfterPlayerMove));
        game.setMoves(null); //TODO-movelog
        gamePvPRepository.save(game);

        this.socketEmitter.distributeMessage(gameId.toString(), message);

    }


    @Override
    public List<MoveDTO> getLegateMoves(Long gameId) throws ResourceNotFoundException, PreconditionFailedException, InvalidDataException {
        checkIsPlayerInGame(getById(gameId), accountService.getCurrent());
        Board board = FenService.parse(getBoardById(gameId));
        return MoveDTO.map(board.getAllLegalMoves());
    }

    @Override
    public void forfeit(Long gameId) throws ResourceNotFoundException, PreconditionFailedException {
        GamePvP game = getById(gameId);
        Account account = accountService.getCurrent();
        checkIsPlayerInGame(game, account);

        GamePvPStatus status = game.getWhitePlayer() == account ? GamePvPStatus.BLACK_WIN : GamePvPStatus.WHITE_WIN;
        game.setStatus(status);
        gamePvPRepository.save(game);

        SocketMessageDTO message = SocketMessageDTO.builder()
                .type(SocketMessageType.END_GAME)
                .chatMessage("Gracz " + account.getNick() + "zrezygnował. " + status.getInfo())
                .build();
        this.socketEmitter.distributeMessage(gameId.toString(), message);
    }

    private Optional<GamePvP> getEmptyRoom(Account account) {
        List<GamePvP> results = getQuerryForEmptyRoom(account).getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public GamePvP startNewGame(GamePvPDTO gamePvPDTO, Account account) {
        GamePvP game = buildGame(gamePvPDTO, account);
        game = gamePvPRepository.save(game);
        game.setChat(chatService.buildChat(game));
        account.getPvpGames().add(game);
        accountRepository.save(account);
        return game;
    }

    private GamePvP buildGame(GamePvPDTO gamePvPDTO, Account account) {
        PlayerColor color = drawColor();
        return GamePvP.builder()
                .whitePlayer(color == PlayerColor.WHITE ? account : null)
                .blackPlayer(color == PlayerColor.BLACK ? account : null)
//                .timePerMove() //TODO-TIMING
                .board(FenService.parse(Board.createStandardBoard()))
                .moves("")
                .status(GamePvPStatus.ROOM)
                .gameStarted(LocalDate.now())
                .build();
    }

    private void validateGameStatus(GamePvPStatus status) throws
            DataMissmatchException, LockedSourceException {
        switch (status) {
            case ROOM:
            case WHITE_MOVE:
            case BLACK_MOVE: {
                break;
            }
            case DRAW:
            case WHITE_WIN:
            case BLACK_WIN: {
                throw new LockedSourceException(ExceptionMessages.GAME_END.getInfo());
            }
            default: {
                throw new DataMissmatchException();
            }
        }
    }

    private GamePvPStatus handleEndOfGame(GamePvP game, MoveDTO moveDTO, GameEndStatus gameEndStatus) {
        GamePvPStatus status;
        GameEndType whiteEndStatus;
        GameEndType blackEndStatus;
        if (gameEndStatus == GameEndStatus.STALE_MATE) {
            status = GamePvPStatus.DRAW;
            whiteEndStatus = GameEndType.DRAW;
            blackEndStatus = GameEndType.DRAW;
        } else {
            if (moveDTO.getStatusPvP() == GamePvPStatus.WHITE_MOVE) {
                status = GamePvPStatus.WHITE_WIN;
                whiteEndStatus = GameEndType.WIN;
                blackEndStatus = GameEndType.LOSE;
            } else {
                status = GamePvPStatus.BLACK_WIN;
                whiteEndStatus = GameEndType.LOSE;
                blackEndStatus = GameEndType.WIN;
            }
        }
        updateStatistics(game.getWhitePlayer().getId(), GameType.PVP, whiteEndStatus, game.getBlackPlayer().getStatistics().getRank());
        updateStatistics(game.getBlackPlayer().getId(), GameType.PVP, blackEndStatus, game.getWhitePlayer().getStatistics().getRank());
        return status;
    }

    @Override
    public ChatDTO getChatConversation(Long gameId) throws ResourceNotFoundException {
        return this.chatService.getConversation(gameId);
    }

    private void checkIsPlayerInGame(GamePvP game, Account account) throws PreconditionFailedException {
        if (game.getWhitePlayer() != account && game.getBlackPlayer() != account) {
            throw new PreconditionFailedException(ExceptionMessages.NOT_PLAYER_IN_THIS_GAME.getInfo());
        }
    }
}
