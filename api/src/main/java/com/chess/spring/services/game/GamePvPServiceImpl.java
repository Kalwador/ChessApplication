package com.chess.spring.services.game;

import com.chess.spring.controllers.game.SocketController;
import com.chess.spring.dto.MoveDTO;
import com.chess.spring.dto.game.GamePvPDTO;
import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.engine.classic.board.Board;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.game.*;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.repositories.GamePvPRepository;
import com.chess.spring.services.account.AccountService;
import com.chess.spring.utils.pgn.FenUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
public class GamePvPServiceImpl extends GameUtils {
    private GamePvPRepository gamePvPRepository;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private SocketController socketController;
    private final EntityManager entityManager;

    @Autowired
    public GamePvPServiceImpl(GamePvPRepository gamePvPRepository,
                              AccountService accountService,
                              AccountRepository accountRepository,
                              SocketController socketController,
                              EntityManager entityManager) {
        this.gamePvPRepository = gamePvPRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.socketController = socketController;
        this.entityManager = entityManager;
    }

    //TODO-PAGINATION
    public Page<GamePvPDTO> getAll(Pageable page) {
        return GamePvPDTO.map(this.gamePvPRepository.findAll(page), page);
    }

    public GamePvP getById(Long gameId) throws InvalidDataException {
        return gamePvPRepository.findById(gameId).orElseThrow(() -> new InvalidDataException("Gra nie odnaleziona"));
    }

    public Long findGame(GamePvPDTO gamePvPDTO) throws ResourceNotFoundException {
        Account account = accountService.getDetails();
        GamePvP game = null;
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
            SocketMessageDTO messageDTO = SocketMessageDTO.builder()
                    .type(SocketMessageType.START_GAME)
                    .sender("SERVER")
                    .build();
            this.socketController.distributeChatMessage(game.getId().toString(), messageDTO);
        } else {
            game = startNewGame(gamePvPDTO, account);
        }
        //TODO - inform other player about game start
        return game.getId();
    }

    private GamePvP startNewGame(GamePvPDTO gamePvPDTO, Account account) {
        GamePvP game = buildGame(gamePvPDTO, account);
        game = gamePvPRepository.save(game);
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
                .board(FenUtilities.createFENFromGame(Board.createStandardBoard()))
                .moves("")
                .status(GamePvPStatus.ROOM)
                .gameStarted(null)
                .build();
    }

    public SocketMessageDTO handleResponse(Long gameId, SocketMessageDTO response) throws DataMissmatchException, LockedSourceException, InvalidDataException, NotExpectedError {
        switch (response.getType()) {
            case JOIN: {
                //TODO zwieksz liczbe graczy obserwujacych gre
                break;
            }
            case LEAVE: {
                //TODO zmniejsz liczbe graczy obserwujacych gre
                break;
            }
            case MOVE: {
                response = executeMove(gameId, response);
            }
            case CHAT: {

            }
        }
        return response;
    }

    public SocketMessageDTO executeMove(Long gameId, SocketMessageDTO message) throws
            InvalidDataException, DataMissmatchException, LockedSourceException, NotExpectedError {
        GamePvP game = getById(gameId);
        validateGame(game.getStatus());

        Board boardAfterPlayerMove = executeMove(game.getBoard(), message.getMoveDTO());

        GameEndStatus gameEndStatus = checkEndOfGame(boardAfterPlayerMove);
        if (gameEndStatus != null) {
            GamePvPStatus status = handleEndOfGame(message, game, gameEndStatus);
            game.setStatus(status);
            message.getMoveDTO().setStatusPvP(status);
        }

        game.setBoard(FenUtilities.createFENFromGame(boardAfterPlayerMove));
        game.setMoves(null); //TODO-movelog

        message.setType(SocketMessageType.MOVE);
        message.getMoveDTO().setStatusPvP(message.getMoveDTO().getStatusPvP().equals(GamePvPStatus.WHITE_MOVE) ? GamePvPStatus.BLACK_MOVE : GamePvPStatus.WHITE_MOVE);
        return message;
    }

    private void validateGame(GamePvPStatus status) throws
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
                throw new LockedSourceException("Gra się zakończyła");
            }
            default: {
                throw new DataMissmatchException("Nie poprawny status nowej gry");
            }
        }
    }

    private GamePvPStatus handleEndOfGame(SocketMessageDTO message, GamePvP game, GameEndStatus gameEndStatus) {
        GamePvPStatus status = null;
        if (gameEndStatus == GameEndStatus.STALE_MATE) {
            status = GamePvPStatus.DRAW;
        }
        switch (message.getMoveDTO().getStatusPvP()) {
            case WHITE_MOVE: {
                status = GamePvPStatus.WHITE_WIN;
                break;
            }
            case BLACK_MOVE: {
                status = GamePvPStatus.BLACK_WIN;
                break;
            }
        }
        return status;
    }

    public List<MoveDTO> getLegateMoves(Long gameId) throws InvalidDataException {
        GamePvP game = getById(gameId);
        Board board = FenUtilities.createGameFromFEN(game.getBoard());
        return MoveDTO.map(board.getAllLegalMoves());
    }

    private Optional<GamePvP> getEmptyRoom(Account account) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GamePvP> cq = cb.createQuery(GamePvP.class);

        Root<GamePvP> root = cq.from(GamePvP.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("status"), GamePvPStatus.ROOM));

        Predicate emptySeat = cb.and(cb.isNull(root.get("whitePlayer")), cb.notEqual(root.get("blackPlayer"), account));
        Predicate notInGame = cb.and(cb.isNull(root.get("blackPlayer")), cb.notEqual(root.get("whitePlayer"), account));
        Predicate join = cb.or(emptySeat, notInGame);
        predicates.add(join);

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        List<GamePvP> results = entityManager.createQuery(cq).getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }
}
