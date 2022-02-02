package com.diegotobalina.framework.core.search;

import lombok.Getter;

@Getter
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;
}
