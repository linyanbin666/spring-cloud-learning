package com.horin.consul.ribbon.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserRibbonController {

  private final RestTemplate restTemplate;
  private final String userServiceUrl;

  public UserRibbonController(RestTemplate restTemplate, @Value("${service-url.user-service}") String userServiceUrl) {
    this.restTemplate = restTemplate;
    this.userServiceUrl = userServiceUrl;
  }

  @GetMapping("/{id}")
  public CommonResult getUser(@PathVariable Long id) {
    return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
  }

  @PostMapping("/create")
  public CommonResult create(@RequestBody User user) {
    return restTemplate.postForObject(userServiceUrl + "/user/create", user, CommonResult.class);
  }

  @PostMapping("/update")
  public CommonResult update(@RequestBody User user) {
    return restTemplate.postForObject(userServiceUrl + "/user/update", user, CommonResult.class);
  }

  @PostMapping("/delete/{id}")
  public CommonResult delete(@PathVariable Long id) {
    return restTemplate.postForObject(userServiceUrl + "/user/delete/{1}", null, CommonResult.class, id);
  }

}
