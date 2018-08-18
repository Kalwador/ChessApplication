//package com.chess.spring.services;
//
//import com.chess.spring.exceptions.account.PlayerNotExistException;
//import com.chess.spring.repositories.PlayerRepository;
//import com.chess.spring.entities.account.Account;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class PlayerService {
//    private PlayerRepository playerRepository;
//
//    @Autowired
//    public PlayerService(PlayerRepository playerRepository) {
//        this.playerRepository = playerRepository;
//    }
//
//    public Account getPlayerById(Long playerId) {
//        Optional<Account> optionalPlayer = playerRepository.findById(playerId);
//        if(optionalPlayer.isPresent()){
//            return optionalPlayer.get();
//        }
//        throw new PlayerNotExistException();
//    }
//}
