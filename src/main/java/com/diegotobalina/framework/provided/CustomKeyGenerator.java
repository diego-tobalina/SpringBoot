package com.diegotobalina.framework.provided;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {

  public Object generate(Object target, Method method, Object... params) {
    return "%s_%s_%s"
        .formatted(
            target.getClass().getSimpleName(),
            method.getName(),
            StringUtils.arrayToDelimitedString(params, "_"));
  }
}
