package com.chess.spring.profile.account;

import com.chess.spring.profile.register.RegisterDTO;
import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
    Account getById(Long id) throws ResourceNotFoundException;

    AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException;

    AccountDTO getCurrentDTO() throws ResourceNotFoundException;

    Account getCurrent() throws ResourceNotFoundException;

    AccountDetails getCurrentDetails() throws ResourceNotFoundException;

    Page<AccountDTO> getAll(Pageable page);

    void updateInfo(AccountDTO accountDTO) throws ResourceNotFoundException;

    void updateDetails(RegisterDTO registerDTO) throws ResourceNotFoundException;

    AccountDTO getProfile(Long accountId) throws ResourceNotFoundException;

    String getNickName(Long accountId) throws ResourceNotFoundException;

    String createNickName(Account account);

    void updateAvatar(MultipartFile file) throws ResourceNotFoundException;
}
