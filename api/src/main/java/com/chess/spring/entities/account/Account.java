package com.chess.spring.entities.account;

import com.chess.spring.entities.game.GamePvE;
import com.chess.spring.entities.game.GamePvP;
import com.chess.spring.models.account.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_details", nullable = false)
    private AccountDetails accountDetails;

    @Size(min = 1)
    private String firstName;

    @Size(min = 1)
    private String lastName;

    @Lob
    @Column(name = "avatar", length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    private Integer age;

    private Gender gender;

    @ColumnDefault("true")
    private boolean isFirstLogin;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<GamePvE> pveGames;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<GamePvP> pvpGames;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isFirstLogin == account.isFirstLogin &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(name, account.name) &&
                Objects.equals(age, account.age) &&
                gender == account.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gender, isFirstLogin);
    }
}
