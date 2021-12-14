package com.diegotobalina.framework.provided.multitenant.tenantdatasource.domain;

import com.diegotobalina.framework.provided.advancedsearch.SpecSearchCriteria;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Builder
public class TenantDataSourceSpecification implements Specification<TenantDataSource> {

  private transient SpecSearchCriteria criteria;

  public TenantDataSourceSpecification(SpecSearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(
      final Root<TenantDataSource> root,
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
