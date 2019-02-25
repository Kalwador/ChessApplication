package com.chess.spring.profile.account;

import com.chess.spring.exceptions.ExceptionMessages;
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
            Image imgFull = ImageIO.read(input);

            BufferedImage bufFull = ImageUtils.toBufferedImage(imgFull);
            BufferedImage bufferedImageFull = ImageUtils.resizeTrick(bufFull, ((BufferedImage) imgFull).getWidth(), ((BufferedImage) imgFull).getHeight());

            ByteArrayOutputStream baosFull = new ByteArrayOutputStream();
            ImageIO.write(bufferedImageFull, "png", baosFull);
            baosFull.flush();
            byte[] imageInByteFull = baosFull.toByteArray();
            String base64Imagef = Base64.getEncoder().encodeToString(imageInByteFull);

            Account account = this.getCurrent();
            account.setAvatar(base64Imagef);

            BufferedImage bufferedImage = ImageUtils.resizeTrick(bufFull, 100, 100);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            Base64.getEncoder().encodeToString(imageInByte);

            account.setThumbnail(base64Imagef);
            accountRepository.save(account);

        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }


    public Account findPlayerByNickOrName(String playerNick) throws ResourceNotFoundException {
        return accountRepository.findByNick(playerNick).
                orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.PLAYER_NOT_FOUND.getInfo()));
    }

    public boolean existByNick(String nick) {
        return accountRepository.existsByNick(nick);
    }
}
