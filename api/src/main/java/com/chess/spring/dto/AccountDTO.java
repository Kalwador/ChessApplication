package com.chess.spring.dto;

import com.chess.spring.entities.account.Account;
import com.chess.spring.models.account.Gender;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

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
    private String name;

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
                .name(account.getName())
                .build();
    }

    public static Page<AccountDTO> map(Page<Account> all) {
        return new PageImpl<>(all.getContent().stream()
                .map(AccountDTO::map)
                .collect(Collectors.toList()));
    }
}
