package com.chess.spring.security.oauth;

import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.profile.account.details.AccountDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public LoginServiceImpl(AccountDetailsRepository accountDetailsRepository) {
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
