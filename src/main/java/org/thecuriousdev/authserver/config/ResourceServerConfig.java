package org.thecuriousdev.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.thecuriousdev.authserver.service.CustomUserDetailsService;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

  private AuthenticationManager authenticationManager;
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  public ResourceServerConfig(AuthenticationManager authenticationManager,
      CustomUserDetailsService customUserDetailsService) {
    this.authenticationManager = authenticationManager;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers()
        .antMatchers("/login", "/oauth/authorize")
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
    .formLogin()
    .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.parentAuthenticationManager(authenticationManager)
        .userDetailsService(customUserDetailsService);
  }
}
