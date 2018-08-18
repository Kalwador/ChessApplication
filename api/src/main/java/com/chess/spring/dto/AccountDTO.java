package com.chess.spring.dto;

import com.chess.spring.entities.account.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private String id;
    private String email;

    public static Account map(AccountDTO accountDTO) {
        Account account = new Account();
        return account;
    }
}