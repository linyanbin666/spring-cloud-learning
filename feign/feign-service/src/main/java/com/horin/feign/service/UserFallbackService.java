package com.horin.feign.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import org.springframework.stereotype.Service;

@Service
public class UserFallbackService implements UserService {

  @Override
  public CommonResult create(User user) {
    return CommonResult.fail();
  }

  @Override
  public CommonResult<User> getUser(Long id) {
    User defaultUser = new User(-1L, "defaultUser");
    return CommonResult.success(defaultUser);
  }

  @Override
  public CommonResult update(User user) {
    return CommonResult.fail();
  }

  @Override
  public CommonResult delete(Long id) {
    return CommonResult.fail();
  }

}
