package com.horin.oauth2.server;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/getCurrentUser")
  public Object getCurrentUser(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    String token = header.split("\\s+")[1];
    return Jwts.parser()
        .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
  }

}