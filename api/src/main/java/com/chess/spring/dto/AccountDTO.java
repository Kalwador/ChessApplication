package com.chess.spring.dto;

import com.chess.spring.entities.account.Account;
import com.chess.spring.models.account.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private byte[] avatar;
    private boolean isFirstLogin;

    public static AccountDTO map(Account account) {
        return AccountDTO.builder()
                .username(account.getAccountDetails().getUsername())
                .email(account.getAccountDetails().getEmail())
                .age(account.getAge())
                .avatar(account.getAvatar())
                .isFirstLogin(account.isFirstLogin())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .gender(account.getGender())
                .build();
    }
}
