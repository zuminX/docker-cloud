package com.zumin.dc.common.core.utils.functional;

@FunctionalInterface
public interface QuadrConsumer<T, U, V, R> {

  void accept(T t, U u, V v, R r);

  default QuadrConsumer<T, U, V, R> condition(QuadrPredicate<T, U, V, R> predicate) {
    return (t, u, v, r) -> {
      if (predicate.test(t, u, v, r)) {
        accept(t, u, v, r);
      }
    };
  }
}