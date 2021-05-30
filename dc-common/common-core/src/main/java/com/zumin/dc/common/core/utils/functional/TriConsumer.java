package com.zumin.dc.common.core.utils.functional;

@FunctionalInterface
public interface TriConsumer<T, U, V> {

  void accept(T t, U u, V v);

  default TriConsumer<T, U, V> condition(TriPredicate<T, U, V> predicate) {
    return (t, u, v) -> {
      if (predicate.test(t, u, v)) {
        accept(t, u, v);
      }
    };
  }
}