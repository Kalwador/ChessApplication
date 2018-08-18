package com.chess.spring.entities.account;

import com.chess.spring.models.player.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_details")
    private AccountDetails accountDetails;

    @Lob
    @Column(name = "avatar", length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    private Integer age;

    private Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(age, account.age) &&
                gender == account.gender;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, age, gender);
        return result;
    }
}
