package com.horin.feign.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", path = "/user", fallback = UserFallbackService.class)
public interface UserService {

  @PostMapping("/create")
  CommonResult create(@RequestBody User user);

  @GetMapping("/{id}")
  CommonResult<User> getUser(@PathVariable("id") Long id);

  @PostMapping("/update")
  CommonResult update(@RequestBody User user);

  @PostMapping("/delete/{id}")
  CommonResult delete(@PathVariable("id") Long id);

}
