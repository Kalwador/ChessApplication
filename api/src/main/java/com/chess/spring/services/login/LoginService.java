package com.chess.spring.services.login;

import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.repositories.AccountDetailsRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j
@Service
public class LoginService implements UserDetailsService {

    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public LoginService(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {" + login + "}");
        String lowercaseLogin = login.toLowerCase();

        Optional<AccountDetails> optionalAccount;
        if (lowercaseLogin.contains("@")) {
            optionalAccount = accountDetailsRepository.findByEmail(lowercaseLogin);
        } else {
            optionalAccount = accountDetailsRepository.findByUsernameCaseInsensitive(lowercaseLogin);
        }

        return optionalAccount.orElseThrow(() -> new UsernameNotFoundException("Account " + login + " was not found in the database"));
    }
}
