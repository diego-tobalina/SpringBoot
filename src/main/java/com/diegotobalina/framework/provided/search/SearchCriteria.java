package com.diegotobalina.framework.provided.search;

import lombok.Getter;

@Getter
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;
}
