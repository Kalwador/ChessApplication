package com.chess.spring.services.register;

import com.chess.spring.dto.RegisterDTO;
import com.chess.spring.entities.account.Account;
import com.chess.spring.entities.account.AccountDetails;
import com.chess.spring.entities.account.Authority;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.models.account.AuthorityType;
import com.chess.spring.models.mail.AccountActivationMail;
import com.chess.spring.models.mail.MailSubject;
import com.chess.spring.repositories.AccountDetailsRepository;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.services.account.AccountService;
import com.chess.spring.services.mail.MailFactory;
import com.chess.spring.utils.ActivationCodeGenerator;
import com.chess.spring.utils.BCryptEncoder;
import com.chess.spring.utils.EmailValidator;
import com.google.common.collect.Sets;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Log4j
@NoArgsConstructor
@Service
public class RegisterService {

    private AccountRepository accountRepository;
    private AccountService accountService;
    private AccountDetailsRepository accountDetailsRepository;
    private MailFactory mailFactory;

    @Autowired
    public RegisterService(AccountRepository accountRepository,
                           AccountDetailsRepository accountDetailsRepository,
                           MailFactory mailFactory,
                           AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.mailFactory = mailFactory;
        this.accountService = accountService;
    }

    public void createNewAccount(RegisterDTO registerDTO) throws InvalidDataException {

        if (!EmailValidator.isEmailValid(registerDTO.getEmail())) throw new InvalidDataException();
        if (registerDTO.getUsername().length() < 4 || registerDTO.getUsername().length() > 25)
            throw new IllegalArgumentException("The username must be longer than 4 and shorter than 25 letters");
        if (registerDTO.getPassword().length() < 8 || registerDTO.getPassword().length() > 25)
            throw new IllegalArgumentException("The password must be longer than 8 and shorter than 25 letters");
        if (accountDetailsRepository.existsByUsername(registerDTO.getUsername()) || accountDetailsRepository.existsByEmail(registerDTO.getEmail()))
            throw new InvalidDataException();

        AccountDetails accountDetails = AccountDetails.builder()
                .email(registerDTO.getEmail())
                .username(registerDTO.getUsername())
                .password(BCryptEncoder.encode(registerDTO.getPassword()))
                .authorities(Sets.newHashSet(new Authority(AuthorityType.ROLE_USER)))
                .activationCode(ActivationCodeGenerator.generateCode())
                .credentialsExpired(false)
                .enabled(false) //need to confirm activation link
                .locked(false)
                .expired(false)
                .build();

        accountDetails = this.accountDetailsRepository.save(accountDetails);

        Account account = Account.builder()
                .accountDetails(accountDetails)
                .isFirstLogin(true)
                .build();

        account.setNick(accountService.createNickName(account));

        this.accountRepository.save(account);

        this.sendActivationCodeInMail(accountDetails);
        log.info("The user : '" + accountDetails.getUsername() + "' was successful registered in application!");
    }

    @PostConstruct
    public static void test() {
//        System.out.println("##");
//        System.out.println(BCryptEncoder.encode(" "));
//        System.out.println("##");
    }

    private void sendActivationCodeInMail(AccountDetails accountDetails) throws InvalidDataException {
        AccountActivationMail mail = AccountActivationMail.builder()
                .activationCode(accountDetails.getActivationCode())
                .username(accountDetails.getUsername())
                .build();
        mail.setRecipient(accountDetails.getEmail());
        mail.setSubject(MailSubject.ACCOUNT_ACTIVATION);

        mailFactory.sendMail(mail);
    }
}
