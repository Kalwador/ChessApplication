package com.chess.spring.services.account;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.dto.RegisterDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.repositories.AccountDetailsRepository;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
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
        return accountRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<AccountDTO> getAll(Pageable page) {
        return AccountDTO.map(this.accountRepository.findAll(page));
    }

    @Override
    public void updateInfo(AccountDTO accountDTO) throws ResourceNotFoundException {
        Account account = getCurrent();
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setAge(accountDTO.getAge());
        account.setGender(accountDTO.getGender());
        accountRepository.save(account);
    }

    @Override
    public void updateDetails(RegisterDTO registerDTO) throws ResourceNotFoundException {
        AccountDetails account = getCurrentDetails();
        if (registerDTO.getUsername() != null && !registerDTO.getUsername().isEmpty()) {
            account.setUsername(registerDTO.getUsername());
        }
        if (registerDTO.getEmail() != null && !registerDTO.getEmail().isEmpty()) {
            account.setEmail(registerDTO.getEmail());
        }
        if (registerDTO.getPassword() != null && !registerDTO.getPassword().isEmpty()) {
            account.setPassword(registerDTO.getPassword());
        }
        accountDetailsRepository.save(account);
    }

    @Override
    public AccountDetails getAccountDetailsByUsername(String username) throws ResourceNotFoundException {
        return accountDetailsRepository.findByUsernameCaseInsensitive(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AccountDTO getCurrentDTO() throws ResourceNotFoundException {
        return AccountDTO.map(getCurrent());
    }

    @Override
    public Account getCurrent() throws ResourceNotFoundException {
        return getCurrentDetails().getAccount();
    }

    @Override
    public AccountDetails getCurrentDetails() throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getAccountDetailsByUsername(userDetails.getUsername());
        } else {
            throw new ResourceNotFoundException("Akcja wymaga zalogowania");
        }
    }

    @Override
    public AccountDTO getProfile(Long accountId) throws ResourceNotFoundException {
        return AccountDTO.mapSimple(getById(accountId));
    }

    @Override
    public String getNickName(Long accountId) throws ResourceNotFoundException {
        Account account = this.getById(accountId);
        return account.getNick();
    }

    @Override
    public String createNickName(Account account) {
        if (account.getNick() != null) {
            return account.getNick();
        }
        if (account.getFirstName() != null || account.getLastName() != null) {
            String nickName = "";
            if (account.getFirstName() != null) {
                nickName += account.getFirstName();
            }
            if (account.getLastName() != null) {
                nickName += account.getLastName();
            }
            return nickName;
        }
        return account.getAccountDetails().getUsername();
    }

    @Override
    public void updateAvatar(MultipartFile file) throws ResourceNotFoundException {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
            Image img = ImageIO.read(input);

            BufferedImage buf = ImageUtils.toBufferedImage(img);
            BufferedImage bufferedImage = ImageUtils.resizeTrick(buf, 100, 100);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            String thumbnail = Base64.getEncoder().encodeToString(imageInByte);

            Account account = this.getCurrent();
            account.setAvatar(file.getBytes());
            account.setThumbnail(thumbnail);
            accountRepository.save(account);
        } catch (IOException ioe) {
            log.error("Image to thumbnail failed ");
        }
    }
}
