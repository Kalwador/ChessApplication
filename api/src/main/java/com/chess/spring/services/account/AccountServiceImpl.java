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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Profile("release")
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public AccountServiceImpl(
            AccountDetailsRepository accountDetailsRepository,
            AccountRepository accountRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getById(Long id) throws ResourceNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profil nie odnaleziony"));
    }

    @Override
    public Page<AccountDTO> getAll(Pageable page) {
        return AccountDTO.map(this.accountRepository.findAll(page));
    }

    @Override
    public void edit(AccountDTO accountDTO) {
        throw new IllegalArgumentException("to do");
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
