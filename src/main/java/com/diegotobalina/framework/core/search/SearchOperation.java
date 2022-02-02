package com.diegotobalina.framework.core.search;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS, EMPTY, NOT_EMPTY, NULL, NOT_NUL;

    @SuppressWarnings({"java:S2386"})
    public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "~", "#", "¿", "/", "$"};

    public static final String OR_PREDICATE_FLAG = "'";

    public static final String ZERO_OR_MORE_REGEX = "*";

    public static SearchOperation getSimpleOperation(final char input) {
        return switch (input) {
            case ':' -> EQUALITY;
            case '!' -> NEGATION;
            case '>' -> GREATER_THAN;
            case '<' -> LESS_THAN;
            case '~' -> LIKE;
            case '#' -> EMPTY;
            case '/' -> NULL;
            case '¿' -> NOT_EMPTY;
            case '$' -> NOT_NUL;
            default -> null;
        };
    }
}
