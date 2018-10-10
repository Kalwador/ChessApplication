package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class AccountServiceImplDEV implements AccountService {


    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountServiceImplDEV(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    public AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException {
        return accountDetailsRepository.findByUsernameCaseInsensitive(username).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
    }

    public AccountDTO getCurrentAccount() throws ResourceNotFoundException {
        return AccountDTO.map(getDetails());
    }

    public Account getDetails() throws ResourceNotFoundException {
        return getCurrent().getAccount();
    }

    public AccountDetails getCurrent() throws ResourceNotFoundException {
        return getAccountDetailsByUsername("user");
    }
}
