package com.horin.oauth2.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenStoreConfig {

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey("test_key");
    return accessTokenConverter;
  }

  @Bean
  public JwtTokenEnhancer jwtTokenEnhancer() {
    return new JwtTokenEnhancer();
  }

}
