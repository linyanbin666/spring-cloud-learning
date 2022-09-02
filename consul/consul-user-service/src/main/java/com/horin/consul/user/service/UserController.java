package com.horin.consul.user.service;

import com.horin.common.CommonResult;
import com.horin.common.User;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  private static final Map<Long, User> userStorage = new ConcurrentHashMap<>();
  private static final AtomicLong idGenerator = new AtomicLong(0);

  @PostMapping("/create")
  public CommonResult<Object> create(@RequestBody User user) {
    log.info("do create user: {}", user);
    user.setId(idGenerator.incrementAndGet());
    userStorage.put(user.getId(), user);
    return CommonResult.success();
  }

  @GetMapping("/{id}")
  public CommonResult<User> getUserById(@PathVariable Long id) {
    User user = userStorage.get(id);
    log.info("get user by id: {}, result: {}", id, user);
    return CommonResult.success(user);
  }

  @GetMapping("/getUserByIds")
  public CommonResult<List<User>> getUserByIds(@RequestParam List<Long> ids) {
    log.info("get user by ids: {}", ids);
    List<User> users = ids.stream().map(userStorage::get).filter(Objects::nonNull)
        .collect(Collectors.toList());
    return CommonResult.success(users);
  }

  @PostMapping("/update")
  public CommonResult<Object> update(@RequestBody User user) {
    log.info("do update user: {}", user);
    userStorage.put(user.getId(), user);
    return CommonResult.success();
  }

  @PostMapping("/delete/{id}")
  public CommonResult<Object> delete(@PathVariable Long id) {
    log.info("delete user by id: {}", id);
    return CommonResult.success();
  }

}
