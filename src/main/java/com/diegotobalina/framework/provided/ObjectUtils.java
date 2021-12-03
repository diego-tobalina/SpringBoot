package com.diegotobalina.framework.provided;

import org.springframework.beans.BeanUtils;

public interface ObjectUtils {

  @SuppressWarnings("java:S3011")
  static void copy(final Object src, final Object dest, final String[] exclude)
      throws IllegalArgumentException {
    BeanUtils.copyProperties(src, dest, exclude);
  }
}
