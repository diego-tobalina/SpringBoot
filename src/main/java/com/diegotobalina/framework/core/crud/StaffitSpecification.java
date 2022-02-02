package com.diegotobalina.framework.core.crud;

import com.diegotobalina.framework.core.search.SpecSearchCriteria;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public abstract class StaffitSpecification<T> implements Specification<T> {

    @Override
    public Predicate toPredicate(
            final Root<T> root,
            @NotNull final CriteriaQuery<?> query,
            @NotNull final CriteriaBuilder builder) {
        String key = getCriteria().getKey();
        Object value = getCriteria().getValue();
        return getPredicate(root.get(key), builder, key, value);
    }

    @Nullable
    private Predicate getPredicate(Path<?> path, CriteriaBuilder builder, String key, Object value) {
        return getCriteria().buildPredicate(path, builder, key, value);
    }

    protected abstract SpecSearchCriteria getCriteria();
}
