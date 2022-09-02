package com.horin.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResult<T> {
  private Integer code;
  private String message;
  private T data;

  private CommonResult(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  private CommonResult(T data) {
    CommonResult<Object> successRes = success();
    this.data = data;
    this.code = successRes.code;
    this.message = successRes.message;
  }

  public static CommonResult<Object> success() {
    return success("操作成功");
  }

  public static CommonResult<Object> success(String message) {
    return new CommonResult<>(200, message);
  }

  public static <T> CommonResult<T> success(T data) {
    return new CommonResult<>(data);
  }

  public static CommonResult<Object> fail() {
    return new CommonResult<>(500, "操作失败");
  }

}
