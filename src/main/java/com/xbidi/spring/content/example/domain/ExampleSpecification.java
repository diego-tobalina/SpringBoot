package com.xbidi.spring.content.example.domain;

import com.xbidi.spring.content.shared.search.SpecSearchCriteria;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Builder
public class ExampleSpecification implements Specification<Example> {

  private transient SpecSearchCriteria criteria;

  public ExampleSpecification(SpecSearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(
      final Root<Example> root,
      @NotNull final CriteriaQuery<?> query,
      @NotNull final CriteriaBuilder builder) {
    String key = criteria.getKey();
    Object value = criteria.getValue();
    return getPredicate(root.get(key), builder, key, value);
  }

  @Nullable
  private Predicate getPredicate(Path<?> path, CriteriaBuilder builder, String key, Object value) {
    return criteria.buildPredicate(path, builder, key, value);
  }
}
