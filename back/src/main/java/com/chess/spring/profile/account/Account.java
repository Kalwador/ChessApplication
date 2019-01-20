package com.chess.spring.profile.account;

import com.chess.spring.game.pve.GamePvE;
import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.profile.account.details.AccountDetails;
import com.chess.spring.profile.invitations.Invitation;
import com.chess.spring.profile.statistics.Statistics;
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_details", nullable = false)
    private AccountDetails accountDetails;

    @Size(min = 1)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick", nullable = false)
    private String nick;

    @Lob
    @Column(name = "avatar", length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @Column(name = "thumbnail", length = 1012)
    private String thumbnail;

    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ColumnDefault("true")
    private boolean isFirstLogin;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Set<GamePvE> pveGames;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Set<GamePvP> pvpGames;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Set<Invitation> invitations;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "statistics_id")
    private Statistics statistics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isFirstLogin == account.isFirstLogin &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(nick, account.nick) &&
                Objects.equals(age, account.age) &&
                gender == account.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gender, isFirstLogin);
    }
}
