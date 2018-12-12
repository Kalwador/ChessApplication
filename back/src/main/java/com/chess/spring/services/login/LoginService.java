package com.chess.spring.services.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService extends UserDetailsService {
    @Override
    @Transactional
    UserDetails loadUserByUsername(String login);
}
