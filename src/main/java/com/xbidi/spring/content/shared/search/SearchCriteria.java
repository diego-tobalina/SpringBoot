package com.xbidi.spring.content.shared.search;

import lombok.Getter;

@Getter
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;
}
