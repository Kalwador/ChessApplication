package com.chess.spring.services.login;

import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.repositories.AccountDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class LoginService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(LoginService.class);


    private AccountDetailsRepository accountDetailsRepository;

    public LoginService(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();

        Optional<AccountDetails> optionalAccount;
        if (lowercaseLogin.contains("@")) {
            optionalAccount = accountDetailsRepository.findByEmail(lowercaseLogin);
        } else {
            optionalAccount = accountDetailsRepository.findByUsernameCaseInsensitive(lowercaseLogin);
        }

        return optionalAccount.orElseThrow(() -> new UsernameNotFoundException("Account " + lowercaseLogin + " was not found in the database"));
    }
}
