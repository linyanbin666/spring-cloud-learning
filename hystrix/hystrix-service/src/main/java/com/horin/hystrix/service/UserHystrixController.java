package com.horin.hystrix.service;

import com.horin.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserHystrixController {

  private final UserService userService;

  public UserHystrixController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/testFallback/{id}")
  public CommonResult testFallback(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @GetMapping("/testCommand/{id}")
  public CommonResult testCommand(@PathVariable Long id) {
    return userService.getUserCommand(id);
  }

  @GetMapping("/testException/{id}")
  public CommonResult testException(@PathVariable Long id) {
    return userService.getUserException(id);
  }

}
