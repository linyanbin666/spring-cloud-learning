package com.horin.oauth2.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private List<User> userList;

  private final PasswordEncoder passwordEncoder;

  public UserService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  public void init() {
    String password = passwordEncoder.encode("123");
    userList = new ArrayList<>();
    userList.add(new User("admin", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
    userList.add(new User("horin", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userList.stream().filter(user -> user.getUsername().equals(username))
        .findFirst().orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
  }

}
