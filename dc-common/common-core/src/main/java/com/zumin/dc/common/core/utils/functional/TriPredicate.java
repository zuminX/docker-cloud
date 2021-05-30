package com.zumin.dc.common.core.utils.functional;

@FunctionalInterface
public interface TriPredicate<T, U, V> {

  boolean test(T t, U u, V v);
}