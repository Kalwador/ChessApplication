package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException;

    AccountDTO getCurrentAccount() throws ResourceNotFoundException;

    Account getDetails() throws ResourceNotFoundException;

    AccountDetails getCurrent() throws ResourceNotFoundException;

    Page<AccountDTO> getAll(Pageable page);

    void edit(AccountDTO accountDTO);

    AccountDTO getProfile(Long accountId) throws ResourceNotFoundException;

    String getNickName(Long accountId) throws ResourceNotFoundException;

    String createNickName(Account account);
}
