package com.chess.spring.entities;

import com.chess.spring.models.player.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details")
public class AccountDetails
//        implements UserDetails
{

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name="id")
    private Account account;

    private String activationCode;

    @Min(0)
    @Max(3)
    @ColumnDefault("0")
    private int incorrectLoginCount;

    private boolean expired;
    private boolean locked;
    private boolean enabled;
    private boolean credentialsExpired;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        throw new NotImplementedException();
//    }

//    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

//    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

//    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpired;
    }
}
