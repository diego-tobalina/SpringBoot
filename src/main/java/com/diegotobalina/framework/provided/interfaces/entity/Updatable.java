package com.diegotobalina.framework.provided.interfaces.entity;

import com.diegotobalina.framework.provided.ObjectUtils;

public interface Updatable {

  default void update(Object src) {
    ObjectUtils.copy(src, this, new String[0]);
  }

  default void update(Object src, String[] exclude) {
    ObjectUtils.copy(src, this, exclude);
  }
}
