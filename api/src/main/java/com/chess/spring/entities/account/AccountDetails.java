package com.chess.spring.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_details")
public class AccountDetails implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "accountDetails")
    private Account account;

    @Column(name = "facebook_id")
    private String facebookId;

    @Email
    @Size(min = 0, max = 50)
    private String email;

    @Getter
    @Column(updatable = false, nullable = false)
    @Size(min = 4, max = 50)
    private String username;

    @Getter
    @Size(min = 4, max = 500)
    @Column(length = 400)
    private String password;

    @Getter
    private boolean enabled;
    private boolean expired;
    private boolean locked;

    @Column(name = "credentials_expired")
    private boolean credentialsExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_authority",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;

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
