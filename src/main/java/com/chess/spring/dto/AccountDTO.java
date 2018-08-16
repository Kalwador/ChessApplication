package com.chess.spring.dto;

import com.chess.spring.entities.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private String id;
    private String email;

    public static Account map(AccountDTO accountDTO) {
        Account account = new Account();
        account.setFacebookId(accountDTO.getId());
        account.setEmail(accountDTO.getEmail());
        return account;
    }
}