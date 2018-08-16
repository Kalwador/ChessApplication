package com.chess.spring.services.register;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.entities.Account;
import com.chess.spring.entities.AccountDetails;
import com.chess.spring.entities.RegisterAttempt;
import com.chess.spring.models.register.AccessToken;
import com.chess.spring.models.register.AccessTokenData;
import com.chess.spring.repositories.AccountDetailsRepository;
import com.chess.spring.repositories.AccountRepository;
import com.chess.spring.repositories.RegisterAttemptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class FacebookService extends RegisterService{
    private FacebookConnector facebookConnector;
    private AccountRepository accountRepository;
    private AccountDetailsRepository accountDetailsRepository;
    private RegisterAttemptRepository registerAttemptRepository;

    @Autowired
    public FacebookService(FacebookConnector facebookConnector,
                           AccountRepository accountRepository,
                           RegisterAttemptRepository registerAttemptRepository,
                           AccountDetailsRepository accountDetailsRepository) {
        this.facebookConnector = facebookConnector;
        this.accountRepository = accountRepository;
        this.registerAttemptRepository = registerAttemptRepository;
        this.accountDetailsRepository = accountDetailsRepository;
    }

    public void facebookRegister(String code, String state) {
//        if (!registerAttemptRepository.existsByUid(state)) {
//            //TODO
//            throw new RuntimeException();
//        }

        try {
            AccessToken accessToken = facebookConnector.getAccessTokenFromCode(code);
            String appAccessToken = facebookConnector.getAppAccessToken();
            AccessTokenData accessTokenData = facebookConnector.inspectAccessToken(accessToken.getAccess_token(), appAccessToken);

            if (!accessTokenData.is_valid() || accessTokenData.getApp_id() != Long.valueOf(facebookConnector.getAPP_ID())) {
                //TODO

                throw new RuntimeException();
            }
            AccountDTO accountDTO = facebookConnector.getUserDetailsFromAccessToken(accessToken.getAccess_token());
            Account account = AccountDTO.map(accountDTO);
            AccountDetails accountDetails = createAccountDetails(account);
            account.setAccountDetails(accountDetails);

            this.accountRepository.save(account);
            this.accountDetailsRepository.save(accountDetails);
        } catch (RuntimeException e) {
            //TODO
            throw new RuntimeException();
        }
    }

    public String getFacebookRegisterUri() {
        String uid = UUID.randomUUID().toString();
        RegisterAttempt registerAttempt = RegisterAttempt.builder()
                .localDate(LocalDate.now())
                .uid(uid).build();
        registerAttemptRepository.save(registerAttempt);
        return "https://www.facebook.com/v2.9/dialog/oauth?client_id=" + facebookConnector.getAPP_ID() + "&redirect_uri=" + facebookConnector.getREDIRECT_URI() + "&state=" + uid;
    }
}
