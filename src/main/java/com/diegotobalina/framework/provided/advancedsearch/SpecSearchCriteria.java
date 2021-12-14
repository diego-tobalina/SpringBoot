package com.diegotobalina.framework.provided.advancedsearch;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SpecSearchCriteria() {}

    public SpecSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(String key, String operation, String prefix, String value, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
            final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

            if (startWithAsterisk && endWithAsterisk) {
                op = SearchOperation.CONTAINS;
            } else if (startWithAsterisk) {
                op = SearchOperation.ENDS_WITH;
            } else if (endWithAsterisk) {
                op = SearchOperation.STARTS_WITH;
            }
        }
        this.key = key;
        this.operation = op;
        this.value = value;
    }

    public Predicate buildPredicate(Path<?> path, CriteriaBuilder builder, String key, Object value) {
        return switch (this.getOperation()) {
            case EQUALITY -> builder.equal(path, value);
            case NEGATION -> builder.notEqual(path.get(key), value);
            case GREATER_THAN -> builder.greaterThan(path.get(key), value.toString());
            case LESS_THAN -> builder.lessThan(path.get(key), value.toString());
            case LIKE -> builder.like(path.get(key), value.toString());
            case STARTS_WITH -> builder.like(path.get(key), value + "%");
            case ENDS_WITH -> builder.like(path.get(key), "%" + value);
            case CONTAINS -> builder.like(path.get(key), "%" + value + "%");
            case EMPTY -> builder.isEmpty(path.get(key));
            case NULL -> builder.isNull(path.get(key));
            case NOT_EMPTY -> builder.isNotEmpty(path.get(key));
            case NOT_NUL -> builder.isNotNull(path.get(key));
        };
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

}
