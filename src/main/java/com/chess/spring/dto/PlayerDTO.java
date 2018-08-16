//package com.chess.spring.dto;
//
//import com.chess.spring.entities.Account;
//import lombok.*;
////import org.hibernate.validator.constraints.Length;
//
//import javax.validation.Valid;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
//import java.util.Optional;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class PlayerDTO {
//    private Long id;
//
//    @NotNull
////    @Length(min = 6, max = 20)
//    private String username;
//
//    @NotNull
////    @Length(min = 6, max = 20)
//    private String password;
//
//    @NotNull
//    @Email
////    @Length(min = 3, max = 50)
//    private String email;
//
//    @Min(1)
//    @NotNull
//    private Integer age;
//
//    public static Account convert(@Valid PlayerDTO playerDTO) {
//        Account account = new Account();
//        Optional<Long> idOptional = Optional.ofNullable(playerDTO.id);
//        if(idOptional.isPresent()){
//            account.setId(playerDTO.id);
//        }
//        account.setUsername(playerDTO.username);
//        account.setPassword(playerDTO.password);
//        account.setAge(playerDTO.age);
//        account.setEmail(playerDTO.email);
//        account.setRoom(null);
//        return account;
//    }
//}
