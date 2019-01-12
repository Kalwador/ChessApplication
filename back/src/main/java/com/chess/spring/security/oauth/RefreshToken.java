package com.chess.spring.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Lob
//    @Length(max = 5120)
    @Column(length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] token;

    @Lob
//    @Length(max = 5120)
    @Column(length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] authentication;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(tokenId, that.tokenId) &&
                Arrays.equals(token, that.token) &&
                Arrays.equals(authentication, that.authentication);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tokenId);
        result = 31 * result + Arrays.hashCode(token);
        result = 31 * result + Arrays.hashCode(authentication);
        return result;
    }
}
