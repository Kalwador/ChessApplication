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
@Table(name = "oauth_access_token")
public class AccesToken {

    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] token;

    private String authentication_id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Lob
    @Column(length = 5120)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] authentication;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccesToken that = (AccesToken) o;
        return Objects.equals(tokenId, that.tokenId) &&
                Arrays.equals(token, that.token) &&
                Objects.equals(authentication_id, that.authentication_id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(clientId, that.clientId) &&
                Arrays.equals(authentication, that.authentication) &&
                Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tokenId, authentication_id, userName, clientId, refreshToken);
        result = 31 * result + Arrays.hashCode(token);
        result = 31 * result + Arrays.hashCode(authentication);
        return result;
    }
}
