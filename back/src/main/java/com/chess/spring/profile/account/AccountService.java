package com.chess.spring.profile.account;

import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.exceptions.PreconditionFailedException;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.profile.register.RegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

public interface AccountService {
    Account getById(Long id) throws ResourceNotFoundException;

    AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException;

    AccountDTO getCurrentDTO() throws ResourceNotFoundException;

    Account getCurrent() throws ResourceNotFoundException;

    AccountDetails getCurrentDetails() throws ResourceNotFoundException;

    Page<AccountDTO> getAll(Pageable page);

    void updateInfo(AccountDTO accountDTO) throws ResourceNotFoundException;

    @Transactional
    void updateDetails(RegisterDTO registerDTO) throws ResourceNotFoundException;

    AccountDTO getProfile(Long accountId) throws ResourceNotFoundException;

    String getNickName(Long accountId) throws ResourceNotFoundException;

    String createNickName(Account account);

    @Transactional
    void updateAvatar(MultipartFile file) throws ResourceNotFoundException, InvalidDataException, PreconditionFailedException;

    Account findPlayerByNickOrName(String playerNick) throws ResourceNotFoundException;

    boolean existByNick(String nick);

    String getAvatar() throws ResourceNotFoundException, InvalidDataException;
}
