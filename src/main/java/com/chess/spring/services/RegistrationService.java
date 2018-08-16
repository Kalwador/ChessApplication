//package com.chess.spring.services;
//
//import com.chess.spring.exceptions.register.EmailNotUnique;
//import com.chess.spring.exceptions.register.UsernameNotUnique;
//import com.chess.spring.repositories.PlayerRepository;
//import com.chess.spring.entities.Account;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class RegistrationService {
//
//    private PlayerRepository playerRepository;
//
//    @Autowired
//    public RegistrationService(PlayerRepository playerRepository) {
//        this.playerRepository = playerRepository;
//    }
//
//    public void checkIfEmailUnique(String email) {
//        Optional<Account> account = this.playerRepository.findByEmail(email);
//        if(account.isPresent()){
//            throw new EmailNotUnique();
//        }
//    }
//
//    public void checkIfUsernameUnique(String username) {
//        Optional<Account> account = this.playerRepository.findByUsername(username);
//        if(account.isPresent()){
//            throw new UsernameNotUnique();
//        }
//    }
//
//    public void registerPlayer(Account account) {
//        playerRepository.save(account);
//    }
//}
