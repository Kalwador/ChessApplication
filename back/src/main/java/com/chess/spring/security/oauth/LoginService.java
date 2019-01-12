package com.chess.spring.security.oauth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService extends UserDetailsService {
    @Override
    @Transactional
    UserDetails loadUserByUsername(String login);
}
