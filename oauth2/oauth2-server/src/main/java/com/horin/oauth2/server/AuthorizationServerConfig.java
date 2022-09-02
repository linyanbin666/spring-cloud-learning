package com.horin.oauth2.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  public AuthorizationServerConfig(
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      UserService userService) {
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager).userDetailsService(userService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("admin")
        .secret(passwordEncoder.encode("admin123"))
        .accessTokenValiditySeconds(3600)
        .refreshTokenValiditySeconds(864000)
        .redirectUris("http://www.baidu.com")
        .scopes("all")
        .authorizedGrantTypes("authorization_code", "password");
  }

}
