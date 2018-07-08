//package com.chess.spring.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@Configuration
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity htpp) throws Exception {
//        htpp
//                .csrf().disable()
//                .antMatcher("/**").authorizeRequests()
//                .antMatchers("/", "/login**", "/webjars/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll();
//    }
//}
