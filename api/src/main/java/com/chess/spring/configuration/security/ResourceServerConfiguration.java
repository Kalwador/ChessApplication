package com.chess.spring.configuration.security;

import com.chess.spring.filters.CORSFilter;
import com.chess.spring.services.security.CustomAuthenticationEntryPoint;
import com.chess.spring.services.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
@Profile("release")
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CORSFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and().logout().logoutUrl("/security/logout").logoutSuccessHandler(customLogoutSuccessHandler)
                .and()
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/security/authorize"))
                .disable().headers()
                .frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/h2/**", "/home**", "/oauth**", "/oauth/**", "/login/**", "/register**", "/info**", "/webjars/**", "/error**", "/swagger-ui.html")
                .permitAll()
                .antMatchers("/socket**","/socket/**").permitAll()//TODO - security over sockets
                .anyRequest()
                .authenticated();
    }
}