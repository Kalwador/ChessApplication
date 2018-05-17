package com.chess.spring;


import com.chess.spring.models.PlayerColor;
import com.chess.spring.repositories.GameRepository;
import com.chess.spring.repositories.PlayerRepository;
import com.chess.spring.repositories.RoomRepository;
import com.chess.spring.entities.Game;
import com.chess.spring.entities.Player;
import com.chess.spring.entities.Room;
import com.chess.spring.models.status.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

@Component
public class InjectData {
    private PlayerRepository playerRepository;
    private RoomRepository roomRepository;
    private GameRepository gameRepository;

    @Autowired
    public InjectData(PlayerRepository playerRepository, RoomRepository roomRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.roomRepository = roomRepository;
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void inject() {
        Player player1 = new Player(1L, "user1", "password", "email1@email.com", 11, null, null);
        Player player2 = new Player(2L, "user2", "password", "email2@email.com", 12, null, null);
        Player player3 = new Player(3L, "user3", "password", "email3@email.com", 13, null, null);
        Player player4 = new Player(4L, "user4", "password", "email3@email.com", 13, null, null);
        Player player5 = new Player(5L, "user5", "password", "email3@email.com", 13, null, null);
        Player player6 = new Player(6L, "user6", "password", "email3@email.com", 13, null, null);
        Player player7 = new Player(7L, "user7", "password", "email3@email.com", 13, null, null);
        Player player8 = new Player(8L, "user8", "password", "email3@email.com", 13, null, null);
        Player player9 = new Player(9L, "user9", "password", "email3@email.com", 13, null, null);

        player1 = playerRepository.save(player1);
        player2 = playerRepository.save(player2);
        player3 = playerRepository.save(player3);
        player4 = playerRepository.save(player4);
        player5 = playerRepository.save(player5);
        player6 = playerRepository.save(player6);
        player7 = playerRepository.save(player7);
        player8 = playerRepository.save(player8);
        player9 = playerRepository.save(player9);

        Room room = new Room(1L, Arrays.asList(player4, player5, player6), GameStatus.WHITE_MOVE.ordinal());
        player4.setRoom(room);
        player5.setRoom(room);
        player6.setRoom(room);

        room = roomRepository.save(room);
        player4 = playerRepository.save(player4);
        player5 = playerRepository.save(player5);
        player6 = playerRepository.save(player6);

        HashMap players = new HashMap();
        players.put(PlayerColor.WHITE, player7);
        players.put(PlayerColor.BLACK, player8);

        Game game = Game.builder()
                .id(1L)
                .players(players)
                // TODO: 05/17/18 set up starting player position
                .status(GameStatus.WHITE_MOVE.ordinal())
                .timeMoveStarted(LocalDate.now())
                .build();

        game = gameRepository.save(game);
        player7 = playerRepository.save(player7);
        player8 = playerRepository.save(player8);
        player9 = playerRepository.save(player9);
    }
}
