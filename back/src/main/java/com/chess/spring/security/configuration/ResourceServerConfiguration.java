package com.chess.spring.security.configuration;

import com.chess.spring.security.CORSFilter;
import com.chess.spring.security.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
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
                .antMatchers("/", "/h2/**", "/home**", "/oauth**", "/oauth/**", "/tokens/**", "/register**", "/info**", "/webjars/**", "/error**", "/swagger-ui.html")
                .permitAll()
                .antMatchers("/sockets**", "/sockets/**").permitAll()//TODO - security over sockets
                .anyRequest()
                .authenticated();
    }
}