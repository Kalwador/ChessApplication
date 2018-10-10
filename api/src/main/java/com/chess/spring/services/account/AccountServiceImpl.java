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
@Profile("release")
public class AccountServiceImpl implements AccountService {


    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountServiceImpl(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    @Override
    public AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException {
        return accountDetailsRepository.findByUsernameCaseInsensitive(username).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
    }

    @Override
    public AccountDTO getCurrentAccount() throws ResourceNotFoundException {
        return AccountDTO.map(getDetails());
    }

    @Override
    public Account getDetails() throws ResourceNotFoundException {
        return getCurrent().getAccount();
    }

    @Override
    public AccountDetails getCurrent() throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getAccountDetailsByUsername(userDetails.getUsername());
        } else {
            throw new ResourceNotFoundException("Gra nie odnaleziona");
        }
    }
}
