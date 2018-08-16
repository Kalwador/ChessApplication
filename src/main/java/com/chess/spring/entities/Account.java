package com.chess.spring.entities;

import com.chess.spring.models.player.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facebook_id")
    private String facebookId;

    @NotEmpty
    @Size(min = 6, max = 25)
    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Size(min = 4, max = 500) //TODO need to increase minimal in release mod
    private String password;

    @Lob
    @Length(max = 5120)
    @Column(name = "avatar")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @Email
    private String email;

    private Integer age;

    private Gender gender;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AccountDetails accountDetails;
}
