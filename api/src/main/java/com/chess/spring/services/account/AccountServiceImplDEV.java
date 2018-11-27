package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.repositories.AccountDetailsRepository;
import com.chess.spring.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class AccountServiceImplDEV implements AccountService {

    private AccountRepository accountRepository;
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountServiceImplDEV(
            AccountDetailsRepository accountDetailsRepository,
            AccountRepository accountRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountRepository = accountRepository;
    }

    private Account getById(Long id) throws ResourceNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profil nie odnaleziony"));
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

    @Override
    public Page<AccountDTO> getAll(Pageable page) {
        return AccountDTO.map(this.accountRepository.findAll(page));
    }

    @Override
    public void edit(AccountDTO accountDTO) {

    }
    @Override
    public AccountDTO getProfile(Long accountId) throws ResourceNotFoundException {
        return AccountDTO.mapSimple(getById(accountId));
    }

    @Override
    public String getNickName(Long accountId) throws ResourceNotFoundException {
        return this.getById(accountId).getNick();
    }

    @Override
    public String createNickName(Account account) {
        if (!account.getNick().isEmpty()) {
            return account.getNick();
        }
        if (!account.getFirstName().isEmpty() || !account.getLastName().isEmpty()) {
            String nickName = "";
            if (!account.getFirstName().isEmpty()) {
                nickName += account.getFirstName();
            }
            if (!account.getLastName().isEmpty()) {
                nickName += account.getLastName();
            }
            return nickName;
        }
        return account.getAccountDetails().getUsername();
    }
}
