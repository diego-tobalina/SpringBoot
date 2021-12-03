package com.diegotobalina.framework.provided.advancedsearch;

import com.google.common.base.Joiner;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public final class SpecificationsBuilder {

  private final List<SpecSearchCriteria> params = new ArrayList<>();

  @SuppressWarnings({"java:S1452"})
  public Specification<?> build(String search, Class<?> clazz)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
          InstantiationException, IllegalAccessException {
    String operationSet = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
    String regex = String.format("(\\w+?)(%s)(\\p{Punct}?)(\\w+?)(\\p{Punct}?),", operationSet);
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(search + ",");
    while (matcher.find()) {
      this.with(
          matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
    }
    return this.build(clazz);
  }

  private Specification<Object> build(Class<?> clazz)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
          InstantiationException, IllegalAccessException {
    if (params.isEmpty()) return null;
    Specification<Object> result = getInstance(params.get(0), clazz);
    for (int i = 1; i < params.size(); i++) {
      SpecSearchCriteria param = params.get(i);
      result =
          param.isOrPredicate()
              ? Specification.where(result).or(getInstance(param, clazz))
              : Specification.where(result).and(getInstance(param, clazz));
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private Specification<Object> getInstance(SpecSearchCriteria param, Class<?> clazz)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
          IllegalAccessException, InvocationTargetException {
    Class<?> clazzForName = Class.forName(clazz.getName());
    Constructor<?> ctor = clazzForName.getConstructor(SpecSearchCriteria.class);
    return (Specification<Object>) ctor.newInstance(param);
  }

  private SpecificationsBuilder with(
      final String orPredicate,
      final String key,
      final String operation,
      final Object value,
      final String prefix,
      final String suffix) {
    SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
    if (op != null) {
      if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
        final boolean startWithAsterisk =
            prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
        final boolean endWithAsterisk =
            suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

        if (startWithAsterisk && endWithAsterisk) {
          op = SearchOperation.CONTAINS;
        } else if (startWithAsterisk) {
          op = SearchOperation.ENDS_WITH;
        } else if (endWithAsterisk) {
          op = SearchOperation.STARTS_WITH;
        }
      }
      params.add(new SpecSearchCriteria(orPredicate, key, op, value));
    }
    return this;
  }

  private SpecificationsBuilder with(
      final String key,
      final String operation,
      final Object value,
      final String prefix,
      final String suffix) {
    return with(null, key, operation, value, prefix, suffix);
  }
}
