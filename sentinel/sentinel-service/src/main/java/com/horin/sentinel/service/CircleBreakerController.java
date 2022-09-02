package com.horin.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.horin.common.CommonResult;
import com.horin.common.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/breaker")
public class CircleBreakerController {

  private final RestTemplate restTemplate;
  private final String userServiceUrl;

  public CircleBreakerController(RestTemplate restTemplate,
      @Value("${service-url.user-service}") String userServiceUrl) {
    this.restTemplate = restTemplate;
    this.userServiceUrl = userServiceUrl;
  }

  @RequestMapping("/fallback/{id}")
  @SentinelResource(value = "fallback", fallback = "handleFallback")
  public CommonResult fallback(@PathVariable Long id) {
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  @RequestMapping("/fallbackException/{id}")
  @SentinelResource(value = "fallbackException", fallback = "handleFallback2", exceptionsToIgnore = {
      NullPointerException.class})
  public CommonResult fallbackException(@PathVariable Long id) {
    if (id == 1) {
      throw new IndexOutOfBoundsException();
    } else if (id == 2) {
      throw new NullPointerException();
    }
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  public CommonResult handleFallback(Long id) {
    User defaultUser = new User(-1L, "defaultUser");
    return CommonResult.success(defaultUser);
  }

  public CommonResult handleFallback2(@PathVariable Long id, Throwable e) {
    log.error("handleFallback2 id:{},throwable class:{}", id, e.getClass());
    User defaultUser = new User(-2L, "defaultUser2");
    return CommonResult.success(defaultUser);
  }

}