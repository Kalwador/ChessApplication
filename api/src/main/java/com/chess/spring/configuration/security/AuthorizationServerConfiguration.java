package com.chess.spring.configuration.security;

import com.chess.spring.models.account.AuthorityType;
import com.chess.spring.services.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${authentication.oauth.clientid}")
    private String PROP_CLIENTID;

    @Value("${authentication.oauth.secret}")
    private String PROP_SECRET;

    @Value("${authentication.oauth.tokenValidityInSeconds}")
    private String PROP_TOKEN_VALIDITY_SECONDS;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LoginService loginService;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(loginService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(PROP_CLIENTID)
                .scopes("read", "write")
                .authorities(AuthorityType.ROLE_ADMIN.name(), AuthorityType.ROLE_USER.name())
                .authorizedGrantTypes("password", "refresh_token")
                .secret(this.passwordEncoder.encode(PROP_SECRET))
                .accessTokenValiditySeconds(Integer.valueOf(PROP_TOKEN_VALIDITY_SECONDS))
                .refreshTokenValiditySeconds(-1);
    }
}