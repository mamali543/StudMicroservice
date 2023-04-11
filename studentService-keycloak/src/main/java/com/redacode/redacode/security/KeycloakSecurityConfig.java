package com.redacode.redacode.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

//protect method controllers based on roles
@EnableGlobalMethodSecurity(prePostEnabled = true)
@KeycloakConfiguration
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    //define (a classic implementation ) the strategy on how to manage the session
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    //define who is going to be responsible for the students and role management in the authenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    //define access permissions
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/studentService/add")
        .authenticated()
        .and()
        .authorizeRequests()
        .antMatchers("/studentService/assign/{studentId}")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/studentService/update")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/studentService/delete/{id}")
        .permitAll()
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll();
    }
}
