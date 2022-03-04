package com.diegotobalina.framework.core.crud.decorators;

import com.diegotobalina.framework.core.util.ObjectUtils;

public interface IUpdatable {

  default void update(Object src) {
    ObjectUtils.copy(src, this, new String[0]);
  }

  default void update(Object src, String[] exclude) {
    ObjectUtils.copy(src, this, exclude);
  }
}
