package com.horin.hystrix.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.net.CookieHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserService {

  private final RestTemplate restTemplate;
  private final String userServiceUrl;

  public UserService(RestTemplate restTemplate, @Value("${service-url.user-service}") String userServiceUrl) {
    this.restTemplate = restTemplate;
    this.userServiceUrl = userServiceUrl;
  }

  @HystrixCommand(fallbackMethod = "getDefaultUser")
  public CommonResult getUser(Long id) {
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  public CommonResult getDefaultUser(@PathVariable Long id) {
    User user = new User();
    user.setId(-1L);
    user.setName("default");
    return CommonResult.success(user);
  }

  @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCommand",
      groupKey = "getUserGroup", threadPoolKey = "getUserThreadPool")
  public CommonResult getUserCommand(Long id) {
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  @HystrixCommand(fallbackMethod = "getDefaultUser2", ignoreExceptions = {NullPointerException.class})
  public CommonResult getUserException(Long id) {
    if (id == 1) {
      throw new IndexOutOfBoundsException();
    } else if (id == 2) {
      throw new NullPointerException();
    }
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  public CommonResult getDefaultUser2(@PathVariable Long id, Throwable e) {
    log.error("getDefaultUser2 id:{},throwable class:{}", id, e.getClass());
    User defaultUser = new User();
    defaultUser.setId(-2L);
    defaultUser.setName("default2");
    return CommonResult.success(defaultUser);
  }

}
