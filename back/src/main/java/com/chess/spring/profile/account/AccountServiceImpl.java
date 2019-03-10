package com.chess.spring.profile.account;

import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.exceptions.PreconditionFailedException;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.profile.account.details.AccountDetailsRepository;
import com.chess.spring.profile.register.RegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Optional;

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
        return accountRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
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
        return accountDetailsRepository
                .findByUsernameCaseInsensitive(username)
                .orElseThrow(ResourceNotFoundException::new);
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
    public void updateAvatar(MultipartFile file) throws ResourceNotFoundException, InvalidDataException, PreconditionFailedException {
        try {
            //TODO need test and better execptions, also check type of file is an image
            ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
            Image imgFull = ImageIO.read(input);

            BufferedImage image = ImageUtils.toBufferedImage(imgFull);
            BufferedImage preparedImage = ImageUtils.decreaseQuality(image);
            BufferedImage avatar = ImageUtils.resizeImage(preparedImage, 200);

            BufferedImage thumbnail = ImageUtils.resizeImage(avatar, 25);
            String encodedThumbnail = ImageUtils.encodeImage(thumbnail);

            if(encodedThumbnail.length() >= 151200 ){
                log.warn("Image is to big to save");
                log.info(Integer.valueOf(encodedThumbnail.length()).toString());
                throw new PreconditionFailedException(ExceptionMessages.IMAGE_TO_BIG.getInfo());
            }

            Account account = this.getCurrent();
            account.setAvatar(ImageUtils.imageToByteArray(avatar));
            account.setThumbnail(encodedThumbnail);
            accountRepository.save(account);
        } catch (FileNotFoundException e) {
            log.error("Image not found" + e);
            throw new InvalidDataException(ExceptionMessages.IMAGE_NOT_VALID.getInfo());
        } catch (IOException ioe) {
            log.error("Exception while reading the Image " + ioe);
            throw new InvalidDataException(ExceptionMessages.IMAGE_NOT_VALID.getInfo());
        }
    }

    public Account findPlayerByNickOrName(String playerNick) throws ResourceNotFoundException {
        return accountRepository.findByNick(playerNick).
                orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.PLAYER_NOT_FOUND.getInfo()));
    }

    public boolean existByNick(String nick) {
        return accountRepository.existsByNick(nick);
    }

    public String getAvatar() throws ResourceNotFoundException, InvalidDataException {
        byte[] avatar = Optional.ofNullable(getCurrent().getAvatar())
                .orElseThrow(() -> new InvalidDataException(ExceptionMessages.AVATAR_NOT_SET.getInfo()));
        return Base64.getEncoder().encodeToString(avatar);
    }
}
