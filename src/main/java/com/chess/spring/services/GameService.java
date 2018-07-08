//package com.chess.spring.services;
//
//import com.chess.spring.dto.GameStatusDTO;
//import com.chess.spring.dto.MoveDTO;
//import com.chess.spring.entities.Game;
//import com.chess.spring.entities.Player;
//import com.chess.spring.entities.Room;
//import com.chess.spring.exceptions.game.GameNotExistException;
//import com.chess.spring.models.player.PlayerColor;
//import com.chess.spring.models.status.GameStatus;
//import com.chess.spring.repositories.GameRepository;
//import com.chess.spring.repositories.PlayerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.validation.Valid;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.HashMap;
//import java.util.Optional;
//
//@Service
//public class GameService {
//    private GameRepository gameRepository;
//    private PlayerRepository playerRepository;
//    private PlayerService playerService;
//
//    @Autowired
//    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, PlayerService playerService) {
//        this.gameRepository = gameRepository;
//        this.playerRepository = playerRepository;
//        this.playerService = playerService;
//    }
//
//    public void startNewGame(Room room) {
//        final Player whitePlayer = room.getPlayers().get(0);
//        final Player blackPlayer = room.getPlayers().get(1);
//
//        HashMap players = new HashMap() {
//            {
//                put(PlayerColor.WHITE, whitePlayer);
//                put(PlayerColor.BLACK, blackPlayer);
//            }
//        };
//
//        Game game = Game.builder()
//                .players(players)
//                // TODO: 05/17/18 set up starting players position
//                .timeMoveStarted(LocalDate.now())
//                .build();
//
//        gameRepository.save(game);
//
//        whitePlayer.setGame(game);
//        blackPlayer.setGame(game);
//
//        playerRepository.save(whitePlayer);
//        playerRepository.save(blackPlayer);
//    }
//
//    public GameStatusDTO getGameStatusDTO(Long playerId) {
//        //TODO get game_id that user play in from security context
//        Game game = getGameFromPlayer(playerService.getPlayerById(playerId));
//        return GameStatusDTO.builder()
////                .gameStatus(GameStatus.convert(game.getStatus()))
//                //TODO add info about board bit set
//                .secondsTillMoveEnd(ChronoUnit.SECONDS.between(game.getTimeMoveStarted(), LocalDate.now()))
//                .build();
//    }
//
//    private Game getGameFromPlayer(Player player) {
//        Optional<Game> optionalGame = Optional.ofNullable(player.getGame());
//        if (optionalGame.isPresent()) {
//            return optionalGame.get();
//        }
//        throw new GameNotExistException();
//    }
//
//    public void makeMove(@Valid MoveDTO moveDTO) {
//        //TODO in progress
//    }
//}
