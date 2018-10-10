package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;

public interface AccountService {
    AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException;

    AccountDTO getCurrentAccount() throws ResourceNotFoundException;

    Account getDetails() throws ResourceNotFoundException;

    AccountDetails getCurrent() throws ResourceNotFoundException;
}
