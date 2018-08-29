package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.account.AccountNotExistsException;
import com.chess.spring.repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountService(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    public AccountDTO getProfile() {
        return AccountDTO.map(getDetails());
    }

    public Account getDetails() {
        return getCurrent().getAccount();
    }

    public AccountDetails getCurrent() throws AccountNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return accountDetailsRepository.findByUsernameCaseInsensitive(userDetails.getUsername()).orElseThrow(AccountNotExistsException::new);
        } else {
            throw new AccountNotExistsException();
        }
    }
}
