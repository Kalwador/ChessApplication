//package com.chess.spring;
//
//import com.chess.spring.entities.Game;
//import com.chess.spring.entities.Account;
//import com.chess.spring.entities.Room;
//import com.chess.spring.models.account.PlayerColor;
//import com.chess.spring.models.status.GameStatus;
//import com.chess.spring.repositories.GameRepository;
//import com.chess.spring.repositories.PlayerRepository;
//import com.chess.spring.repositories.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.HashMap;
//
//@Component
//@Profile("dev")
//public class InjectData {
//    private PlayerRepository playerRepository;
//    private RoomRepository roomRepository;
//    private GameRepository gameRepository;
//
//    @Autowired
//    public InjectData(PlayerRepository playerRepository, RoomRepository roomRepository, GameRepository gameRepository) {
//        this.playerRepository = playerRepository;
//        this.roomRepository = roomRepository;
//        this.gameRepository = gameRepository;
//    }
//
//    @PostConstruct
//    public void inject() {
////        Account player1 = new Account(1L, "user1", "password", "email1@email.com", 11, null, null);
////        Account player2 = new Account(2L, "user2", "password", "email2@email.com", 12, null, null);
////        Account player3 = new Account(3L, "user3", "password", "email3@email.com", 13, null, null);
////        Account player4 = new Account(4L, "user4", "password", "email4@email.com", 14, null, null);
////
////        player1 = playerRepository.save(player1);
////        player2 = playerRepository.save(player2);
////        player3 = playerRepository.save(player3);
////        player4 = playerRepository.save(player4);
////
////        Room room = new Room(1L, Arrays.asList(player1, player2), GameStatus.WHITE_MOVE.ordinal());
////
////        room = roomRepository.save(room);
////
////        HashMap players = new HashMap();
////        players.put(PlayerColor.WHITE, player3);
////        players.put(PlayerColor.BLACK, player4);
////
////        Game game = Game.builder()
////                .id(1L)
////                .players(players)
////                // TODO: 05/17/18 set up starting account position
////                .status(GameStatus.WHITE_MOVE.ordinal())
////                .timeMoveStarted(LocalDate.now())
////                .build();
////
////        game = gameRepository.save(game);
//
//    }
//}
