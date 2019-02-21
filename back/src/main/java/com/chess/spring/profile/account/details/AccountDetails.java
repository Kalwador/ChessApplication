package com.chess.spring.profile.account.details;

import com.chess.spring.profile.account.Account;
import com.chess.spring.security.authority.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details")
public class AccountDetails implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "accountDetails")
    private Account account;

    @Column(name = "facebook_id")
    private String facebookId;

    @Email
    @Size(min = 1, max = 50)
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 4, max = 50)
    @Column(updatable = false, nullable = false, unique = true)
    private String username;

    @Size(min = 4, max = 500)
    @Column(length = 400, nullable = false)
    private String password;

    private boolean enabled;
    private boolean expired;
    private boolean locked;

    @Column(name = "credentials_expired")
    private boolean credentialsExpired;

    @Column(name = "activation_code")
    private String activationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_authority",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority", nullable = false))
    private Set<Authority> authorities;

    @Min(0)
    @Max(3)
    @ColumnDefault("0")
    @Column(name = "incorrect_login_count")
    private int incorrectLoginCount;

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails that = (AccountDetails) o;
        return enabled == that.enabled &&
                expired == that.expired &&
                locked == that.locked &&
                credentialsExpired == that.credentialsExpired &&
                Objects.equals(id, that.id) &&
                Objects.equals(facebookId, that.facebookId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, facebookId, email, username, password, enabled, expired, locked, credentialsExpired, authorities);
    }
}
