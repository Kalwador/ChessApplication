package com.chess.spring.profile.register;

import com.chess.spring.exceptions.*;
import com.chess.spring.profile.account.AccountService;
import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.profile.account.details.AccountDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    private RegisterService registerService;
    private AccountService accountService;
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    public RegisterController(RegisterService registerService,
                              AccountService accountService,
                              AccountDetailsRepository accountDetailsRepository) {
        this.registerService = registerService;
        this.accountService = accountService;
        this.accountDetailsRepository = accountDetailsRepository;
    }

    @PostMapping
    public void registerPlayer(@RequestBody @Valid RegisterDTO registerDTO) throws InvalidDataException {
        this.registerService.createNewAccount(registerDTO);
    }

    @GetMapping(path = "/activate/{username}")
    public boolean isActivated(@PathVariable String username) throws ResourceNotFoundException {
        AccountDetails accountDetails = accountService.getAccountDetailsByUsername(username);

        if (accountDetails.getActivationCode() != null && !accountDetails.getActivationCode().isEmpty()) {
            return false;
        }
        return true;
    }

    @GetMapping(path = "/activate/{username}/{code}")
    public void activateAccount(@PathVariable String username, @PathVariable String code) throws ResourceNotFoundException, DataMissmatchException {
        AccountDetails accountDetails = accountService.getAccountDetailsByUsername(username);

        if(accountDetails.getActivationCode() == null){
            log.trace("Account: " + username + " already activated!");
            throw new IllegalArgumentException(ExceptionMessages.REGISTER_ACCOUNT_ALREADY_ACTIVATED.getInfo());
        }

        if (!accountDetails.getActivationCode().equals(code)) {
            log.trace("Account activation: " + username + " failed!");
            throw new DataMissmatchException(ExceptionMessages.REGISTER_INVALID_ACTIVATION_CODE.getInfo());
        }
        log.trace("Account: " + username + " activated successfully.");
        accountDetails.setActivationCode(null);
        accountDetails.setEnabled(true);
        accountDetailsRepository.save(accountDetails);
    }

}
