package com.horin.oauth2.server;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final TokenStore tokenStore;

  private final JwtAccessTokenConverter jwtAccessTokenConverter;

  private final JwtTokenEnhancer jwtTokenEnhancer;

  public AuthorizationServerConfig(
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      UserService userService,
      TokenStore tokenStore,
      JwtAccessTokenConverter jwtAccessTokenConverter,
      JwtTokenEnhancer jwtTokenEnhancer) {
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.tokenStore = tokenStore;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    this.jwtTokenEnhancer = jwtTokenEnhancer;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> tokenEnhancerList = new ArrayList<>();
    tokenEnhancerList.add(jwtTokenEnhancer);
    tokenEnhancerList.add(jwtAccessTokenConverter);
    enhancerChain.setTokenEnhancers(tokenEnhancerList);
    endpoints.authenticationManager(authenticationManager)
        .userDetailsService(userService)
        .tokenStore(tokenStore)
        .accessTokenConverter(jwtAccessTokenConverter)
        .tokenEnhancer(enhancerChain);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("admin")
        .secret(passwordEncoder.encode("admin123"))
        .accessTokenValiditySeconds(3600)
        .refreshTokenValiditySeconds(864000)
//        .redirectUris("http://www.baidu.com")
        .redirectUris("http://localhost:9501/login")
        .scopes("all")
        .authorizedGrantTypes("authorization_code", "password");
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.tokenKeyAccess("isAuthenticated()"); // 获取密钥需要身份认证，使用单点登录时必须配置
  }

}
