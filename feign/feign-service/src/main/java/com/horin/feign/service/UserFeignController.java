package com.horin.feign.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserFeignController {

  private final UserService userService;

  public UserFeignController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public CommonResult getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PostMapping("/create")
  public CommonResult create(@RequestBody User user) {
    return userService.create(user);
  }

  @PostMapping("/update")
  public CommonResult update(@RequestBody User user) {
    return userService.update(user);
  }

  @PostMapping("/delete/{id}")
  public CommonResult delete(@PathVariable Long id) {
    return userService.delete(id);
  }

}
