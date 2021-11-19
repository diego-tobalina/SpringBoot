package com.xbidi.spring.content.shared.interfaces.domain;

import com.xbidi.spring.content.shared.ObjectUtils;

public interface Updatable {

  default void update(Object src) {
    ObjectUtils.copy(src, this, new String[0]);
  }

  default void update(Object src, String[] exclude) {
    ObjectUtils.copy(src, this, exclude);
  }
}
